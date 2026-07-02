function main(c) {
    var s = c.getServer()
    var pls = s.getAllPlayers()
    
    if (pls.length === 0) return
    
    var rnd = Math.floor(Math.random() * pls.length)
    var player = pls[rnd]
    
    var playerName = player.getName()
    
    c.executeCommand("mp script exec " + playerName + " NewClient.js")
    player.setGameMode(2)
    player.setPosition(310, 5, 341)
}
