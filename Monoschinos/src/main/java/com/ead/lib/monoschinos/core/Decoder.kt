package com.ead.lib.monoschinos.core

fun decoder(encodedString: String): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
    val output = StringBuilder()

    var i = 0
    while (i < encodedString.length) {
        val index1 = chars.indexOf(encodedString[i++])
        val index2 = chars.indexOf(encodedString[i++])
        val index3 = chars.indexOf(encodedString[i++])
        val index4 = chars.indexOf(encodedString[i++])

        val bits = (index1 shl 18) or (index2 shl 12) or (index3 shl 6) or index4

        output.append(((bits shr 16) and 0xff).toChar())
        if (index3 != 64) {
            output.append(((bits shr 8) and 0xff).toChar())
        }
        if (index4 != 64) {
            output.append((bits and 0xff).toChar())
        }
    }

    return output.toString()
}

val chapterRequester = """
                var ajax = document.getElementsByClassName('caplist')[0].getAttribute('data-ajax');
                var regex = /\/(\d+)$/;
                var id = ajax.match(regex);
                var episodesArray = [];
                var buttons = document.getElementsByClassName('btn btn-secondary fw-semibold border-0');
                var allCompleted = false;
            
                for (var i = 0; i < buttons.length; i++) {
            
                    $.ajax({
                        type: "POST",
                        url: "https://monoschinos2.com/ajax/caplist/" + id,
                        data: { "_token": token, p: i + 1 },
                        success: function(data) {
                            $('.caplist .loader').remove();
                            console.log(data);
                            var el = data.caps;
                            for (var j = 0; j < el.length; j++) {
                                episodesArray.push(el[j]);
                            }
                            
                            episodesArray.sort(function(a, b) {
                                return a.episode - b.episode;
                            });
                            lazyLoadInstance.update();
                            
                            allCompleted = true;
                        },
                        dataType: "json",
                        async: false
                    });
            
                }
            
                
                while (!allCompleted) {
                    
                }
            
                episodesArray
            """.trimIndent()

val regexRequested = """https://monoschinos2\.com/ajax/caplist/\d+""".toRegex()