function streamMedia() {
    const mediaName = document.getElementById('mediaNameInput').value;
    const folderPath = document.getElementById('folderInput').value;
    const videoPlayer = document.getElementById('videoPlayer');

    if (!folderPath || !mediaName) {
        $('#errorModalBody').text('Por favor, preencha os campos Pasta e Arquivo!');
        $('#errorModal').modal('show');
        return;
    }

    fetch(`/videos/${mediaName}?folder=${folderPath}`)
        .then(response => {
            if (!response.ok) {
                return response.json();
            }

            return response.blob();
        })
        .then(data => {
            if (data && data.details) {
                const errorMessage = data.details.replace(/.*: /g, '');
                $('#errorModalBody').text(errorMessage);
                $('#errorModal').modal('show');
                throw new Error(errorMessage);
            }

            const videoUrl = URL.createObjectURL(data);
            videoPlayer.src = videoUrl;
        })
        .catch(error => {
            console.error('Error streaming video:', error);
        });
}

function getFileList() {
    const folderInputValue = document.getElementById('folderInput').value;
    if (!folderInputValue) {
        $('#errorModalBody').text('Por favor, preencha o campo de pasta.');
        $('#errorModal').modal('show');
        return;
    }
    fetch(`/videos?folder=${folderInputValue}`)
        .then(response => {
            if (!response.ok) {
                $('#errorModalBody').text('Erro ao obter lista de arquivos.');
                $('#errorModal').modal('show');
                throw new Error('Erro ao obter lista de arquivos.');
            }
            return response.text();
        })
        .then(data => {
            const fileListContainer = document.getElementById('fileList');
            fileListContainer.innerHTML = '';

            const fileList = data.replace('[', '').replace(']', '').split(',').map(file => file.trim());

            fileList.forEach((file, index) => {
                const listItem = document.createElement('li');
                listItem.className = 'list-group-item list-group-item-dark d-flex justify-content-between align-items-center';

                const fileName = document.createTextNode(file);

                const indexBadge = document.createElement('span');
                indexBadge.className = 'badge badge-primary badge-pill';
                indexBadge.textContent = index + 1;

                listItem.appendChild(fileName);
                listItem.appendChild(indexBadge);

                fileListContainer.appendChild(listItem);
            });

            $('#successModalBody').text('Lista de arquivos obtida com sucesso!');
            $('#successModal').modal('show');
        })
        .catch(error => {
            console.error('Erro ao obter lista de arquivos:', error);
        });
}
