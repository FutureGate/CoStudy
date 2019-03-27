$(document).ready(function() {
	$(".ui.form").form({
		fields: {
			userID: {
				identifier: "userID",
				rules: [{
					type: "empty",
					prompt: "아이디를 입력하세요"
				}]
			},
			userPassword: {
				identifier: "userPassword",
				rules: [{
					type: "empty",
					prompt: "비밀번호를 입력하세요"
				}]
	       }
		}
    });
});