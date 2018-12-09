function escreveNome(id) {
	
    $.ajax({
        url : "/"+ id + "/receber",
        success : function(data) {
            $('#result').html(data);
        }
    });
}

function listaEquipamentos() {
    var url = '/equipamentos/lista/1';

    if ($('#searchSurname').val() != '') {
        url = url + '/' + $('#searchSurname').val();
    }

    $("#results-block").load(url);
    console.log("OK");
}

$(function() {

	$('.js-mostra-equipamentos').on('click', function(event) {
		event.preventDefault();

		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');
		console.log(urlReceber);
		$("#results-block").load(urlReceber);
	    console.log("OK");
	});
});

function adicionados() {
    var email = $("#email").val();
    var comboAdicionados = document.getElementById("Adicionado");

    $.ajax({
        type : 'GET',
        url : '/Preconizado/departamento/adicionados',

        crossDomain : true,
        data : ({
            email : email
        }),
        contentType : "application/json; charset=utf-8",
        dataType : "json",

        success : function(data) {
            console.log("sucesso");

            // console.log(data);
            var listaDados = data; // Lista retornada pelo banco

            for (var i = 0; i < listaDados.length; i++) {
                console.log(listaDados[i]);
                $("#adicionado").prepend($('<option>', {

                    value : listaDados[i].cod_Categoria,
                    text : listaDados[i].categoria
                }));

            }

        },
        error : function(data) {
            console.log("erro na funçao");
        }

    });

}
