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
        fileInputLabel.textContent = 'Choose a file';
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
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error uploading file:', error);
        });
    }
}
