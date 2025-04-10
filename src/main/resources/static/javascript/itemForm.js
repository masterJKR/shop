
function bindImg(){
    $(".cs-file-input").on("change",function(event){
        var file = event.target.files;
        var ImageTempUrl = window.URL.createObjectURL(file[0]);

        $(this).next(".item-thumb").attr("src", ImageTempUrl);

        var fileName = $(this).val().split("\\").pop(); //이미지 이름
        var fileExt = fileName.substring(fileName.lastIndexOf(".")+1);//확장자
        fileExt = fileExt.toLowerCase();  // 소문자변환

        if( fileExt !="jpg" && fileExt !="jpeg" && fileExt != "png"
        &&fileExt !="gif" && fileExt != "bmp"){
            alert("jpg, jpeg, png, gif, bmp 파일만 등록 가능합니다.");
            return;
        }

        $(this).prev(".input-group-text").html(fileName);
    });
}