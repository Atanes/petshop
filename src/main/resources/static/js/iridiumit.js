function mostraCampo(){
	if(!document.getElementById("pedigree").checked){
		document.getElementById("divpedigree").style.display = "none";
	}else{
		document.getElementById("divpedigree").style.display = "initial";
	}
}

function preencher_cpf(el) {
	document.getElementById('senha').value = el.value.replace(/\D/g, '');
	document.getElementById('login').value = el.value.replace(/\D/g, '');
}

function filtraRaca() {
    var especie = $("#comboespecie").val();
    
    let dropdown = $('#comboraca');    

    dropdown.empty();

    $.ajax({
        type : 'GET',
        url : '/clientes/animais/listaRacas',

        crossDomain : true,
        data : ({
            especie : especie
        }),
        contentType : "application/json; charset=utf-8",
        dataType : "json",

        success : function(data) {
            console.log("sucesso");

            console.log(data);
            var listaDados = data; // Lista retornada pelo banco
            dropdown.append($('<option></option>').attr('value', '').text('Selecione a raça'));
            
            for (var i = 0; i < listaDados.length; i++) {
                console.log(listaDados[i]);
                dropdown.append($('<option></option>').attr('value', listaDados[i].id).text(listaDados[i].nome));
            }
            
            if(especie != ""){
            	document.getElementById("botaoModal").style.display = "block";
            } else {
            	document.getElementById("botaoModal").style.display = "none";
            }
            
             

        },
        error : function(data) {
        	console.log(data)
            console.log("erro na funçao");
        }
      
    });

}


$(function() {
	mostraCampo();
	
	$(document).on('change', '.custom-file-input', function (event) {
	    $(this).next('.custom-file-label').html(event.target.files[0].name);
	    /*document.getElementById('foto').value = event.target.files[0].name;
	    document.getElementById('fotoType').value = event.target.files[0].type;*/
	})
});
