// Se importan los js (modulos)
import {getMembers, popup_integrantes} from "./members.js"
import {compile, printOut} from "./compiler.js"



window.onload = function(){
	document.getElementById("openModal").style.visibility = 'hidden';
	document.getElementById("about").onclick = getMembers;
	document.getElementById("run").onclick = compile;
	document.getElementById("reset-out").onclick = function (){	
		if(confirm("Decea limpiar el área Resultado?")){
			document.getElementById("out-area").value = '';
		}
	};
	document.getElementById("reset-code").onclick = function (){
		if(confirm("Desea limpiar el área Programable?")){
			document.getElementById("code-area").value = '';
		}
	};
}

// Se exportan los js (modulos)
export{
	getMembers, compile, printOut, popup_integrantes
}
	