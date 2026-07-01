var URL = Java.type("java.net.URL")
var Scanner = Java.type("java.util.Scanner")

/* example usage: 
function main(c) {
    var raw = getFile("https://githubusercontent.com/MrKisher0/bestfromsolly/main/info.json")
    var parsed = JSON.parse(raw)

    for (var author = 0; author < parsed.authors.length; author++) {
        c.send(parsed.authors[author])
    }
}
*/


function getFile(url) {
    url = new URL(url)

    var stream = url.openStream()

    var scanner = new Scanner(stream, "UTF-8").useDelimiter("\\A")

    var text = scanner.hasNext() ? scanner.next() : ""

    scanner.close()
    stream.close()

    return text
}
