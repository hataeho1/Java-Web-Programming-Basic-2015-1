var formList = document.querySelectorAll('.answerWrite input[type=submit]');
for ( var j=0 ; j < formList.length ; j++) {
	formList[j].addEventListener('click', writeAnswers, false);
}

function writeAnswers(e) {
	 e.preventDefault();
	 
	 var answerForm = e.currentTarget.form;
	 var url = "/api/addanswer.next";
	 var params = "questionId=" + answerForm[0].value + "&writer=" + answerForm[1].value + "&contents=" + answerForm[2].value;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 location.reload(true);
		 }
	 }
	 
	 request.send(params);
}

var deleteBtnList = document.querySelectorAll('.comment a');
for ( var j=0 ; j < deleteBtnList.length ; j++) {
	deleteBtnList[j].addEventListener('click', deleteAnswers, false);
}

function deleteAnswers(e) {
	 e.preventDefault();

	 var answerId = e.currentTarget.dataset.answerno;
	 var questionId = e.currentTarget.dataset.questionno;

	 var url = "/api/deleteanswer.next";
	 var params = "answerId=" + answerId + "&questionId=" + questionId;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 location.reload(true);
		 }
	 }
	 
	 request.send(params);
}

var deleteQuestionBtn = document.querySelector("#main a:nth-of-type(2)");
deleteQuestionBtn.addEventListener('click', deleteQuestion, false);
function deleteQuestion(e) {
	 e.preventDefault();
	 
	 var memberName = e.currentTarget.dataset.membername;
	 var questionId = e.currentTarget.dataset.questionid;

	 var url = "/deletequestion.next";
	 var params = "memberName=" + memberName + "&questionId=" + questionId;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 var result = request.responseText;
			 result = JSON.parse(result);
			 
			 if(result.errorMessage == undefined) {
				 location.href="/list.next";
			 } else {
				 alert(result.errorMessage);
			 }
			 
		 }
	 }
	 
	 request.send(params);
}

