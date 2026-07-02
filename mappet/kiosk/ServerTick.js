// server tick

function main(c) {
    var serv = c.getServer()
    var w = c.getWorld()
    
    var isClientRaw = serv.getStates().getString("isClient")
    var isClient = false
    
    if (isClientRaw && isClientRaw !== "null" && isClientRaw !== "") {
        isClient = JSON.parse(isClientRaw)
    }
    
    var client_randtime = Math.floor(Math.random() * 100)
    
    if (!isClient) {
        if (client_randtime === 99) {
            c.executeCommand("mp script exec ~ SetClient.js")
        }
    }
}
