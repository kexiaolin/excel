<html>

<script>

    function create(){
        var xhr= new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/dc_war/rest/hello/export1", true);
        xhr.responseType = "blob";
        xhr.onload = function (oEvent) {
            var content = xhr.response;
            var fileName = "aa.xlsx"; // 保存的文件名
            var elink = document.createElement('a');
            elink.download = filename;
            elink.style.display = 'none';

            var blob = new Blob([content]);
            elink.href = URL.createObjectURL(blob);

            document.body.appendChild(elink);
            elink.click();

            document.body.removeChild(elink);
        };
        xhr.send();

    }
</script>
<body>
<h2>Hello World!</h2>
</body>


<input type="button" value="download" onclick="create()">
</html>

