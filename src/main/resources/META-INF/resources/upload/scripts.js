document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('fileInput').addEventListener('change', handleFileSelect);
});

function handleFileSelect(event) {
    const fileInput = event.target;
    const fileInputLabel = document.getElementById('fileInputLabel');
    const fileName = fileInput.files[0]?.name;

    if (fileName) {
        fileInputLabel.textContent = fileName;
    } else {
        fileInputLabel.textContent = 'Escolha um arquivo';
    }
}

function uploadFile() {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    const customVideoName = document.getElementById('customVideoName').value;

    if (file) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('fileName', customVideoName || file.name);

        fetch('/videos/upload', {
            method: 'POST',
            body: formData,
        })
        .then(response => {
            if (response.status === 409) {
                return response.text().then(data => {
                    $('#errorModalBody').text(data);
                    $('#errorModal').modal('show');
                    throw new Error(data);
                });
            }
            if (!response.ok) {
                return response.text().then(data => {
                    throw new Error(data || 'Erro desconhecido.');
                });
            }
            return response.text();
        })
        .then(data => {
            $('#successModalBody').text(data);
            $('#successModal').modal('show');
            console.log(data);
        })
        .catch(error => {
            console.error('Error uploading file:', error.message);
        });
    }
}