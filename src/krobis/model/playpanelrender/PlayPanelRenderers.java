package krobis.model.playpanelrender;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

public class PlayPanelRenderers {

	public interface Renderable {
		void render(Graphics2D g);
	}

	public enum ShapeFillOption {

		/*
		 * Here's where shit gets weird. If you recall from the render() method of the RenderShapes
		 * class, there was more stuff going on that I told you to ignore. There are two things
		 * occurring.
		 * First, the method checks if field centeringOptions is not null. If it isn't, it
		 * calls member method adjust() on Shape toDraw and stores the result as the new Shape.
		 * This is a great way to avoid using horrible horrible switch-case blocks with enums:
		 * if each enum does something different, give the functionality to the enum itself and just
		 * call the method. adjust() aims to center the given shape along some number of its axes.
		 * See the ShapeCenteringOption enum class for more on that.
		 *
		 * And second, the render() method checks if field fillOption is not null. If it isn't,
		 * it calls member method consume(), given the graphics object and the shape. This is
		 * identical to the above logic: give the enums the work and dynamic dispatch will work
		 * the rest. Note the use of a BiConsumer to accept two different objects and to return
		 * nothing (Bi = 2, consumer = return nothing)
		 */

		FILLED(Graphics2D::fill),
		OUTLINE(Graphics2D::draw);

		private BiConsumer<Graphics2D, Shape> render;

		ShapeFillOption(BiConsumer<Graphics2D, Shape> render) {
			this.render = render;
		}

		public void consume(Graphics2D g, Shape shape) {
			this.render.accept(g, shape);
		}

	}

	public enum ShapeCenteringOption {

		/*
		 * There are four options here: center along no axis, one of either the horizontal
		 * or vertical axis, or both axes. Each has different operation, but the method is
		 * the same: the UnaryOperator of type Shape accepts a Shape, potentially modifies
		 * it, and returns it (Unary = 1, Operator = modify and return). Lambdas make this
		 * quick and easy.
		 */


		ALONG_X((Shape shape) -> {
			// shift the shape's coordinates to center it along the x-axis

			throw new UnsupportedOperationException("Unimplemented");
		}),

		ALONG_Y((Shape shape) -> {
			throw new UnsupportedOperationException("Unimplemented");
		}),

		ALONG_BOTH((Shape shape) -> {
			// shift the shape's coordinates to center it along both of its axes
			Rectangle bounds = shape.getBounds();
			int w = bounds.width;
			int h = bounds.height;

			AffineTransform tr = new AffineTransform();
			tr.translate(-w / 2, -h / 2);

			return tr.createTransformedShape(shape);
		}),

		ALONG_NONE((Shape shape) -> {
			// do nothing
			return shape;
		});

		private UnaryOperator<Shape> adjust;

		ShapeCenteringOption(UnaryOperator<Shape> adjust) {
			this.adjust = adjust;
		}

		public Shape adjustToCenter(Shape shape) {
			return this.adjust.apply(shape);
		}
	}

	private interface Colorable {

		Renderable setColor(Color color);
		Renderable setColors(Color[] colors);
	}

	private interface Fontable {
		Renderable setFont(Font font);
		Renderable setFonts(Font[] fonts);
	}

	public static class RenderShapes implements Renderable, Colorable {

		private ShapeCenteringOption[] centeringOptions;

		private Shape[] shapes;

		private Color colorDefault = Color.BLACK;

		private Color[] colors;

		private ShapeFillOption[] isFilledEach;

		public RenderShapes(Shape[] shapes) {
			this.shapes = shapes;
		}

		public RenderShapes setColor(Color color) {
			if (this.colors != null) {
				throw new IllegalArgumentException("Setting the default color after setting " +
						"color array is nonsensical.");
			}
			this.colorDefault = color;
			return this;
		}

		public RenderShapes setColors(Color[] colors) {
			if (colors.length != this.shapes.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}
			this.colors = colors;
			return this;
		}

		public RenderShapes setShapeFillOptions(ShapeFillOption[] isFilledEach) {
			if (this.shapes.length != isFilledEach.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}

			this.isFilledEach = isFilledEach;
			return this;
		}

		public RenderShapes setShapeCenteringOptions(ShapeCenteringOption[] centerData) {
			if (this.shapes.length != centerData.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}

			this.centeringOptions = centerData;
			return this;
		}

		@Override
		public void render(Graphics2D g) {
			Color color = new Color(this.colorDefault.getRGB());
			for (int i = 0; i < shapes.length; i++) {
				Shape toDraw = shapes[i];

				if (this.centeringOptions != null) {
					toDraw = this.centeringOptions[i].adjustToCenter(toDraw);
				}

				if (this.colors != null) {
					color = this.colors[i];
				}
				g.setColor(color);

				if (this.isFilledEach != null) {
					this.isFilledEach[i].consume(g, toDraw);
				} else {
					ShapeFillOption.OUTLINE.consume(g, toDraw);
				}

			}
		}

	}

	public static class CenterText implements Renderable, Colorable, Fontable {

		private int width;

		private int[] yCoords;

		private String[] lines;

		private Color colorDefault = Color.BLACK;

		private Color[] colors;

		private Font fontDefault = new Font("Garamond", Font.PLAIN, 32);

		private Font[] fonts;

		public CenterText(String[] lines, int width, int[] yCoords) {
			if (lines.length != yCoords.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}
			this.lines = lines;
			this.width = width;
			this.yCoords = yCoords;
		}

		public CenterText setFont(Font font) {
			this.fontDefault = font;
			return this;
		}

		public CenterText setFonts(Font[] fonts) {
			if (fonts.length != this.lines.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}
			this.fonts = fonts;
			return this;
		}

		public CenterText setColor(Color color) {
			this.colorDefault = color;
			return this;
		}

		public CenterText setColors(Color[] colors) {
			if (colors.length != this.lines.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}
			this.colors = colors;
			return this;
		}


		@Override
		public void render(Graphics2D g) {

			Font font = new Font(this.fontDefault.getAttributes());
			Color color = new Color(this.colorDefault.getRGB());

			// for each line...
			for (int i = 0; i < lines.length; i++) {
				// get the line (as String and char[]), y coord, length of the string, and
				// set current x coord to the center
				String thisLine = lines[i];
				char[] chars = thisLine.toCharArray();
				int thisYCoord = yCoords[i];
				int thisLength = chars.length;

				// get and set the current font if possible
				if (this.fonts != null) {
					font = fonts[i];
				}
				g.setFont(font);
				// same for color
				if (this.colors != null) {
					color = colors[i];
				}
				g.setColor(color);

				// get the current FontMetrics
				FontMetrics metrics = g.getFontMetrics(font);
				// determine the space needed for the first half of the line and deduct it
				// from xCoord
				int space = metrics.charsWidth(chars, 0, thisLength);
				int spaceLeft = this.width - space;
				int xCoord = (spaceLeft / 2);

				g.drawString(thisLine, xCoord, thisYCoord);
			}

		}

	}

	public static class CenterLines extends CenterText {

		public CenterLines(String[] lines, Font font, int width, int firstYCoord) {
			super(lines, width, determineYCoords(font, firstYCoord, lines.length));
			this.setFont(font);
		}


		public CenterLines(String[] lines, Font[] fonts, int width, int firstYCoord) {
			super(lines, width, determineYCoords(fonts, firstYCoord));
			this.setFonts(fonts);
		}

		private static int[] determineYCoords(Font font, int currentYCoord, int numLines) {
			int[] yCoords = new int[numLines];
			Canvas canvas = new Canvas();
			int spaceForLine = canvas.getFontMetrics(font).getHeight();
			for (int i = 0; i < numLines; i++) {
				yCoords[i] = currentYCoord;
				currentYCoord += spaceForLine;
			}
			return yCoords;
		}

		private static int[] determineYCoords(Font[] fonts, int currentYCoord) {
			int[] yCoords = new int[fonts.length];
			Canvas canvas = new Canvas();
			for (int i = 0; i < fonts.length; i++) {
				Font rn = fonts[i];
				int spaceForLine = canvas.getFontMetrics(rn).getHeight();
				yCoords[i] = currentYCoord;
				currentYCoord += spaceForLine;
			}

			return yCoords;
		}
	}

}
