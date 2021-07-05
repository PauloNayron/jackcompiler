package analisadorLexico.utils

class LimpaComentarios {
    fun exec (src: String): Map<Int, String> {
        val lines = src.split("\n")

        val mapLine = HashMap<Int, String>()
        var count = 1
        var linhaComentada = false
        for (line in lines) {
            if (linhaComentada && !line.contains("*/")) {
                mapLine.put(count++, "")
            } else if (line.contains("/*")) {
                val split = line.split("/*")
                if (split[1].contains("*/")) {
                    linhaComentada = false
                    val split2 = split[1].split("*/")[1]
                    mapLine.put(count++, "${split[0]}${split2}")
                } else {
                    linhaComentada = true
                    mapLine.put(count++, split[0])
                }
            } else if (line.contains("*/")) {
                linhaComentada = false
                mapLine.put(count++, line.split("*/")[1])
            } else {
                mapLine.put(count++, line.split("//")[0])
            }
        }
        return mapLine
    }
}