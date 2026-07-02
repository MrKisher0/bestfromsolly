var orders = ["kiosk:hotdog_empty", "kiosk:hotdog_ketchup", "kiosk:hotdog_senf", "kiosk:hotdog_sovsem"]

var costs = {
    hotdog: 3,
    beer: 2,
    soda: 2,
}

var char1 = {
    0: ["Жадный", "Ни за что не платите за заказ."],
    1: ["Молчаливый", "Пользуйтесь своей возможностью говорить без особого желания."],
    2: ["Гиперактивный", "Вы очень активная личность."],
    3: ["Агрессивный", "Вы агрессивный и вспыльчивый."],
    4: ["Грубый", "Вы грубите всем."],
    5: ["Чиловый", "Всегда расслабленный."],
    6: ["Усталый", "Говоришь без особой охоты."]
}

var char2 = {
    0: ["Немой", "Вы не можете использовать голосовой чат."],
    1: ["Нетерпеливый", "Быстро злится."],
    2: ["Пьяный", "ДоЛбАеБ..."],
    3: ["Слепой", "Не зрячий."]
}

function main(c) {
    var order = generateOrder()
    var character = generateCharacter()
    var order_text = ""
    var types = ["без соуса", "с кетчупом", "с горчицей", "с кетчупом и горчицей"]
    var totalCost = 0
    
    if (order.hotdog && order.hotdog > 0) {
        var hotdogType = Math.floor(Math.random() * 4)
        order.hotdogType = hotdogType
        var count = order.hotdog
        totalCost += count * costs.hotdog
        
        if (count == 1) {
            order_text += count + " Хотдог (" + types[hotdogType] + ")\n"
        } else {
            order_text += count + " Хотдога (" + types[hotdogType] + ")\n"
        }
    }
    
    if (order.beer && order.beer > 0) {
        var count = order.beer
        totalCost += count * costs.beer
        if (count == 1) {
            order_text += count + " Пиво\n"
        } else {
            order_text += count + " Пива\n"
        }
    }
    
    if (order.soda && order.soda > 0) {
        var count = order.soda
        totalCost += count * costs.soda
        if (count == 1) {
            order_text += count + " Сода\n"
        } else {
            order_text += count + " Соды\n"
        }
    }
    
    var orderId = ""
    if (order.hotdogType !== undefined) {
        orderId = orders[order.hotdogType]
    }
    
    var fullOrder = {
        text: order_text,
        hotdogType: order.hotdogType,
        id: orderId,
        character: character,
        totalCost: totalCost
    }
    
    c.getServer().getStates().setString("current_order", JSON.stringify(fullOrder))
    
    var charText = "§6Характер клиента:\n§e" + character[0][0] + " - " + character[0][1] + "\n§e" + character[1][0] + " - " + character[1][1]
    
    var char1text = c.subject.setupHUD("char1text")
    c.subject.changeHUDMorph("char1text", 0, mappet.createMorph("")) // make with text of character 1
    
    var char2text = c.subject.setupHUD("char2text")
    c.subject.changeHUDMorph("char2text", 0, mappet.createMorph("")) // make with text of character 2
    
    c.executeCommand("/give @s kiosk:dollar " + totalCost)
}

function generateCharacter() {
    var char1_keys = Object.keys(char1)
    var char2_keys = Object.keys(char2)
    
    var char1_sel = char1_keys[Math.floor(Math.random() * char1_keys.length)]
    var char2_sel = char2_keys[Math.floor(Math.random() * char2_keys.length)]
    
    return [char1[char1_sel], char2[char2_sel]]
}

function generateOrder() {
    var hotdog = Math.round(Math.random() * 2)
    var beer = Math.round(Math.random() * 2)
    var soda = Math.round(Math.random() * 2)
    
    var order = {}
    
    if (hotdog != 0) {
        var hotdog_count = Math.round(Math.random() * 3) + 1
        order.hotdog = hotdog_count
    }
    
    if (beer != 0) {
        var beer_count = Math.round(Math.random() * 3) + 1
        order.beer = beer_count
    }
    
    if (soda != 0) {
        var soda_count = Math.round(Math.random() * 3) + 1
        order.soda = soda_count
    }
    
    if (hotdog == 0 && beer == 0 && soda == 0) {
        order.hotdog = 1
    }
    
    return order
}
