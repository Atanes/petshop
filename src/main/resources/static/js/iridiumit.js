function adicionados() {
    var valor = $("#s1").val();
    var comboAdicionados = document.getElementById("Equipamento");

    $.ajax({
        type : 'GET',
        url : '/adicionados',

        crossDomain : true,
        data : ({
            valor : valor
        }),
        contentType : "application/json; charset=utf-8",
        dataType : "json",

        success : function(data) {
            console.log("sucesso");

            console.log(data);
            var listaDados = data; // Lista retornada pelo banco

            for (var i = 0; i < listaDados.length; i++) {
                console.log(listaDados[i]);
                $("#equip").prepend($('<option>', {

                    value : listaDados[i].id,
                    text : listaDados[i].descricao
                }));
            }

        },
        error : function(data) {
            console.log("erro na funçao");
        }

    });

}

function comboEquipamentos() {
    var valor = $("#cbocliente").val();
    
    let dropdown = $('#cboequipamento');    

    dropdown.empty();

    $.ajax({
        type : 'GET',
        url : '/atendimento/cboEquipamentos',

        crossDomain : true,
        data : ({
            valor : valor
        }),
        contentType : "application/json; charset=utf-8",
        dataType : "json",

        success : function(data) {
            console.log("sucesso");

            console.log(data);
            var listaDados = data; // Lista retornada pelo banco
            dropdown.append($('<option></option>').attr('value', '').text('Selecione o equipamento'));
            
            for (var i = 0; i < listaDados.length; i++) {
                console.log(listaDados[i]);
                dropdown.append($('<option></option>').attr('value', listaDados[i].id).text(listaDados[i].descricao));
            }

        },
        error : function(data) {
        	console.log(data)
            console.log("erro na funçao");
        }
      
    });

}
