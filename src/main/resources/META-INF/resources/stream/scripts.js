document.addEventListener('DOMContentLoaded', function () {
    getFileList();
});

function streamMedia() {
    const mediaName = document.getElementById('mediaNameInput').value;
    const folderPath = document.getElementById('folderInput').value;
    const videoPlayer = document.getElementById('videoPlayer');

    fetch(`/videos/${mediaName}?folder=${folderPath}`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Video not found');
        }
        return response.blob();
    })
    .then(blob => {
        const videoUrl = URL.createObjectURL(blob);
        videoPlayer.src = videoUrl;
    })
    .catch(error => {
        console.error('Error streaming video:', error);
    });
}

function getFileList() {
    fetch(`/videos?folder=${document.getElementById('folderInput').value}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch file list');
            }
            return response.text();
        })
        .then(data => {
            console.log('Data received:', data);

            document.getElementById("fileList").innerHTML = "";

            const fileList = data.replace('[', '').replace(']', '').split(',').map(file => file.trim());

            var table = document.createElement("table");
            table.className = "file-table";

            fileList.forEach((file, index) => {
                var row = table.insertRow();
                var indexCell = row.insertCell(0);
                indexCell.textContent = index + 1;
                var fileCell = row.insertCell(1);
                fileCell.textContent = file;
            });

            document.getElementById("fileList").appendChild(table);
        })
        .catch(error => {
            console.error('Error getting file list:', error);
        });
}
