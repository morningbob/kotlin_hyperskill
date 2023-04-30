import java.awt.Color
import java.awt.image.BufferedImage  

fun drawLines(): BufferedImage {
  // Add your code here
    val newImage = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)

    val graphics = newImage.createGraphics()
    graphics.color = Color.RED
    graphics.drawLine(0, 0, 200, 200)
    graphics.color = Color.GREEN
    graphics.drawLine(200, 0, 0, 200)

    return newImage
}