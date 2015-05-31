<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" language="javascript">
	function checkfile(sender) {
		var validExts = new Array(".xlsx");
		var fileExt = sender.value;
		fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
		if (validExts.indexOf(fileExt) < 0) {
			alert("請選擇正確Excel檔案格式");
			document.getElementById("go").disabled = true;
			return false;
		} else
			document.getElementById("go").disabled = false;
		return true;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Excel檔案上傳</title>
</head>
<body>
	<center>
		<form method="POST" name="upload" enctype="multipart/form-data"
			action="uploadExcel">
			檔案上傳:<br /> <input type="file" name="file"
				accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				onchange="checkfile(this)"> <br /> <br /> <input
				type="submit" value="上傳" id="go">
		</form>
	</center>
</body>
</html>