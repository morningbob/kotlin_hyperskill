package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    //val args = readln()
    //val argsList = args.split(" ")
/*
    val inFile = args[1]
    val outFile = args[3]

    val originalFile = File(inFile)
    val originalImage = ImageIO.read(originalFile)

    val newImage = convertNegativeImage(originalImage)

    saveImage(newImage, outFile)

    return

 */
/*
    println("Enter rectangle width: ")
    val width = readln().toInt()
    println("Enter rectangle height: ")
    val height = readln().toInt()
    println("Enter output image name: ")
    val filename = readln()

    val image = drawRectangle(width, height)
    saveImage(image, filename)

 */
}

private fun convertNegativeImage(originalImage: BufferedImage) : BufferedImage {
    val newImage = BufferedImage(originalImage.width, originalImage.height, BufferedImage.TYPE_INT_RGB)

    for (row in 0..originalImage.height - 1) {
        for (column in 0..originalImage.width - 1) {
            val pixel = originalImage.getRGB(column, row)
            val color = Color(pixel)
            val newColor = Color(255 - color.red, 255 - color.green,
                255 - color.blue)
            newImage.setRGB(column, row, newColor.rgb)
        }
    }

    return newImage
}

private fun drawRectangle(width: Int, height: Int) : BufferedImage {
    val newImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = newImage.createGraphics()

    graphics.color = Color.RED

    graphics.drawLine(0, 0, width-1, height-1)
    graphics.drawLine(0, height-1, width-1, 0)

    return newImage
}

private fun saveImage(bufferedImage: BufferedImage, filename: String) {
    val file = File(filename)

    ImageIO.write(bufferedImage, "png", file)
}
