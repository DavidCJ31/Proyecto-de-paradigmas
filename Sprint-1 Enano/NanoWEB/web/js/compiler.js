async function compile() {
    let coding = document.getElementById("code-area").value;
    if(coding != ''){
        const headers = new Headers();
        const body = JSON.stringify({
            code: coding
        })
        const init = {
            method: 'POST',
            headers,
            body
        }
        const response = await fetch('http://localhost:8080/compile', init);
        const mediaType = response.headers.get('content-type');
        let data = mediaType.includes('json') ? await response.json() : 0;
        if (data !== 0) {
            printOut(data);
        } else {
            console.log('No JSON');
        }
    }
    
}

function printOut(data){
	document.getElementById("out-area").value = data.out;
}

export{
	compile,
	printOut
}
