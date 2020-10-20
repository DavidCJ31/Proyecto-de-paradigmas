async function getMembers(){
    try{
        const response = await fetch('http://localhost:8080/members');
        const mediaType = response.headers.get('content-type');
        if(response.ok){
            let data = mediaType.includes('json') ? await response.json() : 0;
            console.log(data);
            if (data !== 0) {
                popup_integrantes(data);
            } else {
                console.log('No JSON');
            }
        }
        
    }catch(error){
        alert('Error al extraer los datos en la base de datos');
    }
    }


function popup_integrantes(data) {
    if(data){
        document.getElementById("openModal").click();
        let popup = document.getElementById('infor');
        let integrantes = data;
        console.log(integrantes);
        popup.innerHTML = "Project: " + data.project + "<br>" +
            "Course: " + data.course + "<br>" +
            "Instance: " + data.instance + "<br>" +
            "Cycle: " + data.cycle + "<br>" +
            "Organization: " + data.organization + "<br>" +
            "Project Site: " + data.projectSite + "<br>" +
            "Team code: " + data.code + "<br><br>";
        let members = data.members;
        members.forEach((x) => {
            popup.innerHTML += "ID: " + x.id + " " +
                "Name: " + x.name + " " + x.surname + "<br>";
        });
    }
}

export{
	getMembers,
	popup_integrantes
}