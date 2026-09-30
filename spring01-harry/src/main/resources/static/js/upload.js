
function uploadFile() {
    let fileInput = document.getElementById("fileInput");
    let file = fileInput.files[0];

    let reader = new FileReader();
    reader.onload = function () {
        let base64Full = reader.result;
        console.log(base64Full);
        let base64 = base64Full.split(",")[1];
        console.log(base64.length);

        let data = {
            fileName: file.name,
            contentType: file.type,
            base64: base64
        }

        let url = "/members/test33"
        fetch(url, {
            method: "POST",
            headers: { "Content-type" : "application/json" },
            body: JSON.stringify(data)
        }).then(function (response) {

            console.log("HTTP Status:", response.status);
            console.log("OK:", response.ok);

            if (!response.ok) {
                throw new Error("HTTP Error: " + response.status);
            }

            return response.text();
        }).then(function (text) {
            console.log("Server Response:", text);
        }).catch(error => {
            console.error('Failed to fetch page: ', error)
        })
    }
    reader.readAsDataURL(file);
}