package krobis.model.playpanelrender;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

public class PlayPanelRenderers {

	/*
	 * (continuing from drawPlayPanel() in SubState...)
	 * This is a disaster and I love it. The most important thing here is the
	 * below interface. It says that any Renderable needs a render method, which
	 * takes a Graphics2D instance and renders stuff onto it. Easy enough. Hop over
	 * the enums for now and go into the RenderShapes static class below...
	 */

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

	public static class RenderShapes implements Renderable {

		/*
		 * RenderShapes is Renderable. Scroll down to see its render() method (ignore the
		 * rest for right now)
		 */


		private ShapeCenteringOption[] centeringOptions;

		private Shape[] shapes;

		private ShapeFillOption[] isFilledEach;

		public RenderShapes(Shape[] shapes) {
			this.shapes = shapes;
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

		/*
		 * Here it is. The purpose of this RenderShapes class is to draw a bunch of shapes onto
		 * the playpanel. But render() doesn't accept any Shapes. The Shapes come from the
		 * constructor of this class, which is called *before* render() is called, by nature
		 * of the constructor. The rest of this stuff isn't too important for right now. Jump
		 * below to the CenterText static class (skip over DrawShapes and DrawText)...
		 */

		@Override
		public void render(Graphics2D g) {
			for (int i = 0; i < shapes.length; i++) {
				Shape toDraw = shapes[i];

				if (this.centeringOptions != null) {
					toDraw = this.centeringOptions[i].adjustToCenter(toDraw);
				}

				if (this.isFilledEach != null) {
					this.isFilledEach[i].consume(g, toDraw);
				} else {
					ShapeFillOption.OUTLINE.consume(g, toDraw);
				}

			}
		}

	}

	public static class DrawShapes implements Renderable {

		private Shape[] shapes;

		public DrawShapes(Shape[] shapes) {
			this.shapes = shapes;
		}

		@Override
		public void render(Graphics2D g) {
			for (Shape shape : shapes) {
				g.draw(shape);
			}
		}

	}


	public static class DrawText implements Renderable {

		private int[] xCoords;
		private int[] yCoords;

		private String[] lines;

		public DrawText(String[] lines, int[] xCoords, int[] yCoords) {
			this.xCoords = xCoords;
			this.yCoords = yCoords;
		}

		@Override
		public void render(Graphics2D g) {
			throw new UnsupportedOperationException("Unimplemented!");
		}
	}


	public static class CenterText implements Renderable {

		/*
		 * This one seems more complicated. It follows the same basic idea: take the stuff
		 * i need into the constructor, then use it in render(). Look through the render() method
		 * below.
		 * If you don't really care anymore, then i'm done here. Hopefully you somewhat see how
		 * these things come together in the StateNew class where we started; in particular,
		 * see if you understand everything in the method initSubstate1(). It's confusing
		 * and not well commented. My b.
		 * If you wanna see some more weird shit, go back up to the top of this file and look into
		 * the first enum class (ShapeFillOption).
		 * P.S. I should take the Font array in through a Builder method, since it's an optional
		 * field. I'll fix that later.
		 */

		private int width;

		private int[] yCoords;

		private String[] lines;

		private Font[] fonts = null;

		public CenterText(String[] lines, int width, int[] yCoords) {
			if (lines.length != yCoords.length) {
				throw new IllegalArgumentException("Input arrays must have the same lengths!");
			}
			this.lines = lines;
			this.width = width;
			this.yCoords = yCoords;
		}

		public CenterText setFonts(Font[] fonts) {
			this.fonts = fonts;
			return this;
		}


		@Override
		public void render(Graphics2D g) {

			if (this.fonts == null) {
				this.renderWithoutFonts(g, lines);
			} else {
				this.renderWithFonts(g, lines);
			}
		}


		/**
		 * To center each line of text at its relative y coordinate, based on the used font,
		 * onto a graphics2d instance
		 *
		 * @param lines   The text to render, one String per line
		 * @param g Graphics object
		 */
		private void renderWithoutFonts(Graphics2D g, String[] lines) {
			// center at half the width
			int centerAbout = width / 2;
			// get the current font
			Font font = g.getFont();

			// for each line...
			for (int i = 0; i < lines.length; i++) {
				// get the line (as String and char[]), y coord, length of the string, and
				// set current x coord to the center
				String thisLine = lines[i];
				char[] chars = thisLine.toCharArray();
				int thisCoord = yCoords[i];
				int thisLength = thisLine.length();
				int xCoord = centerAbout;

				// get the current FontMetrics
				FontMetrics metrics = g.getFontMetrics(font);
				// determine the space needed for the first half of the line and deduct it
				// from xCoord
				int space = metrics.charsWidth(chars, 0, thisLength / 2);
				xCoord -= space;

				g.drawString(thisLine, xCoord, thisCoord);
			}
		}

		/**
		 * To center each line of text at its relative y coordinate, based on the used font,
		 * onto a graphics2d instance
		 *
		 * @param lines   The text to render, one String per line
		 * @param g Graphics object
		 */
		private void renderWithFonts(Graphics2D g, String[] lines) {

			// center at half the width
			int centerAbout = width / 2;

			// for each line...
			for (int i = 0; i < lines.length; i++) {
				// get the line (as String and char[]), y coord, length of the string, and
				// set current x coord to the center
				String thisLine = lines[i];
				char[] chars = thisLine.toCharArray();
				int thisCoord = yCoords[i];
				int thisLength = thisLine.length();
				int xCoord = centerAbout;

				// get and set the current font
				Font font = fonts[i];
				g.setFont(font);

				// get the current FontMetrics
				FontMetrics metrics = g.getFontMetrics(font);
				// determine the space needed for the first half of the line and deduct it
				// from xCoord
				int space = metrics.charsWidth(chars, 0, thisLength / 2);
				xCoord -= space;

				g.drawString(thisLine, xCoord, thisCoord);
			}
		}

	}

}
