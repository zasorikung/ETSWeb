package com.extosoft.web.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;


public class ResizeImage {

	public BufferedImage resizeImage(BufferedImage image, int areaWidth, int areaHeight) {
        float scaleX = (float) areaWidth / image.getWidth();
        float scaleY = (float) areaHeight / image.getHeight();
        float scale = Math.min(scaleX, scaleY);
        int w = Math.round(image.getWidth() * scale);
        int h = Math.round(image.getHeight() * scale);

        int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        boolean scaleDown = scale < 1;

        if (scaleDown) {
            // multi-pass bilinear div 2
            int currentW = image.getWidth();
            int currentH = image.getHeight();
            BufferedImage resized = image;
            while (currentW > w || currentH > h) {
                currentW = Math.max(w, currentW / 2);
                currentH = Math.max(h, currentH / 2);

                BufferedImage temp = new BufferedImage(currentW, currentH, type);
                Graphics2D g2 = temp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(resized, 0, 0, currentW, currentH, null);
                g2.dispose();
                resized = temp;
            }
            return resized;
        } else {
            Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC : RenderingHints.VALUE_INTERPOLATION_BILINEAR;

            BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(image, 0, 0, w, h, null);
            g2.dispose();
            return resized;
        }
    }
	
	/**
	 * Convenience method that returns a scaled instance of the
	 * provided {@code BufferedImage}.
	 *
	 * @param img the original image to be scaled
	 * @param targetWidth the desired width of the scaled instance,
	 *    in pixels
	 * @param targetHeight the desired height of the scaled instance,
	 *    in pixels
	 * @param hint one of the rendering hints that corresponds to
	 *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
	 *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
	 *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
	 *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
	 * @param higherQuality if true, this method will use a multi-step
	 *    scaling technique that provides higher quality than the usual
	 *    one-step technique (only useful in downscaling cases, where
	 *    {@code targetWidth} or {@code targetHeight} is
	 *    smaller than the original dimensions, and generally only when
	 *    the {@code BILINEAR} hint is specified)
	 * @return a scaled version of the original {@code BufferedImage}
	 */
	
	 public BufferedImage getScaledInstance(
	            BufferedImage img,
	            int targetWidth,
	            int targetHeight,
	            Object hint,
	            boolean higherQuality) {
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
		    // Use multi-step technique: start with original size, then
		    // scale down in multiple passes with drawImage()
		    // until the target size is reached
		    w = img.getWidth();
		    h = img.getHeight();
		} else {  
		    // Use one-step technique: scale directly from original
		    // size to target size with a single drawImage() call
		    w = targetWidth;
		    h = targetHeight;
		}
	
		do {
		   if (higherQuality) {
		      if (w > targetWidth) {
		         w /= 2;
		         if (w < targetWidth) {
		            w = targetWidth;
		         }
		      } else {
		         w = targetWidth;
		      }
		      if (h > targetHeight) {
		         h /= 2;
		         if (h < targetHeight) {
		        h = targetHeight;
		         }
		      } else {
		         h = targetHeight;
		      }
		   }
		   BufferedImage tmp = null;
		   if (img.getType() == 0) {
		      tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		   } else {
		      tmp = new BufferedImage(w, h, img.getType());
		   }
		   Graphics2D g2 = tmp.createGraphics();
//		   g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		   g2.drawImage(ret, 0, 0, w, h, null);
		   g2.dispose();
		   g2.setComposite(AlphaComposite.Src);

		   g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		   g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		   g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
		   ret = tmp;
		} while (w != targetWidth || h != targetHeight);
		return ret;
	}
}
