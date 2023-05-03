package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Double.sum
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt

fun main(args: Array<String>) {

    val inFile = args[1]
    val outFile = args[3]

    val originalFile = File(inFile)
    val originalImage = ImageIO.read(originalFile)

    val newImage = createEnergyImage(originalImage)
    saveImage(newImage, outFile)

    //val newImage = convertNegativeImage(originalImage)

    //saveImage(newImage, outFile)

    return

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

private fun createEnergyImage(originalImage: BufferedImage) : BufferedImage {
    var energyGraph = Array(originalImage.height) { DoubleArray(originalImage.width) }
    var maxEnergy = 0.0

    for (row in 0..originalImage.height - 1) {
        for (column in 0..originalImage.width - 1) {
            var leftPixel : Color? = null
            var rightPixel : Color? = null
            var upperPixel : Color? = null
            var lowerPixel : Color? = null
            // top left corner
            if (column == 0 && row == 0) {
                leftPixel = getPixel(originalImage, column, row)
                rightPixel = getPixel(originalImage, column + 2, row)
                upperPixel = getPixel(originalImage, column, row)
                lowerPixel = getPixel(originalImage, column, row + 2)
                // top right corner
            } else if (column == originalImage.width - 1 &&
                row == 0) {
                leftPixel = getPixel(originalImage, column - 2, row)
                rightPixel = getPixel(originalImage, column, row)
                upperPixel = getPixel(originalImage, column, row)
                lowerPixel = getPixel(originalImage, column, row + 2)
                // bottom left corner
            } else if (column == 0 && row == originalImage.height - 1) {
                leftPixel = getPixel(originalImage, column, row)
                rightPixel = getPixel(originalImage, column + 2, row)
                upperPixel = getPixel(originalImage, column, row - 2)
                lowerPixel = getPixel(originalImage, column, row)
                // bottom right corner
            } else if (column == originalImage.width - 1 &&
                row == originalImage.height - 1) {
                leftPixel = getPixel(originalImage, column - 2, row)
                rightPixel = getPixel(originalImage, column, row)
                upperPixel = getPixel(originalImage, column, row - 2)
                lowerPixel = getPixel(originalImage, column, row)
            } else if (column == 0) {
                leftPixel = getPixel(originalImage, column, row)
                rightPixel = getPixel(originalImage, column + 2, row)
                upperPixel = getPixel(originalImage, column, row - 1)
                lowerPixel = getPixel(originalImage, column, row + 1)
            } else if (row == 0) {
                leftPixel = getPixel(originalImage, column - 1, row)
                rightPixel = getPixel(originalImage, column + 1, row)
                upperPixel = getPixel(originalImage, column, row)
                lowerPixel = getPixel(originalImage, column, row + 2)
            } else if (column == originalImage.width - 1) {
                leftPixel = getPixel(originalImage, column - 2, row)
                rightPixel = getPixel(originalImage, column, row)
                upperPixel = getPixel(originalImage, column, row - 1)
                lowerPixel = getPixel(originalImage, column, row + 1)
            } else if (row == originalImage.height - 1) {
                leftPixel = getPixel(originalImage, column - 1, row)
                rightPixel = getPixel(originalImage, column + 1, row)
                upperPixel = getPixel(originalImage, column, row - 2)
                lowerPixel = getPixel(originalImage, column, row)
            }
            else {
                leftPixel = getPixel(originalImage, column - 1, row)
                rightPixel = getPixel(originalImage, column + 1, row)
                upperPixel = getPixel(originalImage, column, row - 1)
                lowerPixel = getPixel(originalImage, column, row + 1)
            }


            //val leftPixel = getPixel(originalImage, col, ro)
            //val rightPixel = getPixel(originalImage, col, ro)
            val xGradient = calculateGradient(leftPixel, rightPixel)
            //val upperPixel = getPixel(originalImage, col, ro)
            //val lowerPixel = getPixel(originalImage, col, ro)
            val yGradiant = calculateGradient(upperPixel, lowerPixel)
            val energy = calculateEnergy(xGradient, yGradiant)
            if (energy > maxEnergy) {
                maxEnergy = energy
            }
            energyGraph[row][column] = energy
        }
    }

    return createImage(energyGraph, maxEnergy, originalImage)
}

private fun createImage(energyGraph: Array<DoubleArray>, maxEnergy: Double, originalImage: BufferedImage) : BufferedImage {
    //val newGraph = Array(energyGraph.size) { IntArray(energyGraph[0].size) }
    val newImage = BufferedImage(originalImage.width, originalImage.height, BufferedImage.TYPE_INT_RGB)
    //val newimage = BufferedImage

    for (row in 0..energyGraph.size - 1) {
        for (column in 0..energyGraph[0].size - 1) {
            val energy = normalizeEnergy(energyGraph[row][column], maxEnergy)
            val color = Color(energy, energy, energy)
            newImage.setRGB(column, row, color.rgb)
        }
    }

    return newImage
}

private fun calculateEnergy(xGradient: Double, yGradiant: Double) : Double {
    return sqrt(xGradient + yGradiant)
}

private fun getPixel(originalImage: BufferedImage, x: Int, y: Int) : Color {
    val pixel = originalImage.getRGB(x, y)
    return Color(pixel)
}

private fun calculateGradient(pixelOne: Color, pixelTwo: Color) : Double {
    val red = (pixelOne.red - pixelTwo.red.toDouble()).pow(2)
    val green = (pixelOne.green - pixelTwo.green.toDouble()).pow(2)
    val blue = (pixelOne.blue - pixelTwo.blue.toDouble()).pow(2)
    return red + green + blue
}

private fun normalizeEnergy(energy: Double, maxEnergy: Double) : Int {
    return (255.0 * energy / maxEnergy).toInt()
}

private fun saveImage(bufferedImage: BufferedImage, filename: String) {
    val file = File(filename)

    ImageIO.write(bufferedImage, "png", file)
}

/*
    println("Enter rectangle width: ")
    val width = readln().toInt()
    println("Enter rectangle height: ")
    val height = readln().toInt()
    println("Enter output image name: ")
    val filename = readln()

    val image = drawRectangle(width, height)
    saveImage(image, filename)

private fun drawRectangle(width: Int, height: Int) : BufferedImage {
    val newImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = newImage.createGraphics()

    graphics.color = Color.RED

    graphics.drawLine(0, 0, width-1, height-1)
    graphics.drawLine(0, height-1, width-1, 0)

    return newImage
}
 */
