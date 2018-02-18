package example

import java.awt.image.BufferedImage
import java.io.{File, PrintWriter, StringWriter}
import java.util.concurrent.{ForkJoinPool, ForkJoinTask, RecursiveAction}
import javax.imageio.ImageIO

class ForkBlur(source: Array[Int], start: Int, length: Int, destination: Array[Int])
  extends RecursiveAction {

  // Processing window size; should be odd.
  private val blurWidth = 15

  protected def computeDirectly() {
    val sidePixels = (blurWidth - 1) / 2
    for (index <- start until (start + length)) {
      // Calculate average.
      var rt: Float = 0
      var gt: Float = 0
      var bt: Float = 0
      for (mi <- -sidePixels to sidePixels) {
        val mIndex = Math.min(
          Math.max(mi + index, 0),
          source.length - 1
        )
        val pixel = source(mIndex)
        rt += ((pixel & 0x00ff0000) >> 16).toFloat / blurWidth
        gt += ((pixel & 0x0000ff00) >> 8).toFloat / blurWidth
        bt += ((pixel & 0x000000ff) >> 0).toFloat / blurWidth
      }
      // Reassemble destination pixel.
      val dpixel = (0xff000000) |
        ((rt.toInt) << 16) |
        ((gt.toInt) << 8) |
        ((bt.toInt) << 0)

      destination(index) = dpixel
    }
  }

  protected val threshold = 100000

  protected def compute(): Unit = {
    if (length < threshold) {
      computeDirectly()
    } else {
      val split = length / 2

      ForkJoinTask.invokeAll(
        new ForkBlur(source, start, split, destination),
        new ForkBlur(source, start + split, length - split, destination)
      )
    }
  }
}

object Main {
  def main(args: Array[String]): Unit = {
     try {
       val img = ImageIO.read(new File("fork-join.jpg"))

       val (width, height) = (img.getWidth, img.getHeight)

       val imgArray: Array[Int] = new Array[Int](img.getHeight * img.getWidth)
       for(x <- 0 until width)
         for(y <- 0 until height)
           imgArray(x + y*width) = img.getRGB(x, y)

       val destination: Array[Int] = new Array[Int](img.getHeight * img.getWidth)

       // 1. Create a task that represents all of the work to be done.
       // source image pixels are in src
       // destination image pixels are in dst
       // RecursiveAction extends extends ForkJoinTask<Void>
       val fb: RecursiveAction = new ForkBlur(imgArray, 0, imgArray.length, destination)

       // 2.Create the ForkJoinPool that will run the task.
       val pool = new ForkJoinPool()

       // 3.Run the task.
       println("go")
       pool.invoke(fb)
       println("finished")

       val blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR)
       for(x <- 0 until width)
         for(y <- 0 until height)
           blurredImage.setRGB(x, y, destination(x + y*width))

       ImageIO.write(blurredImage, "jpeg", new File("blurred.jpg"))

     } catch {
      case t: Throwable => {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        println(t.getMessage)
        println(sw)
      }
    } finally {
    }
  }
}
