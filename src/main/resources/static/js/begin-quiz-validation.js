document.addEventListener('DOMContentLoaded', function () {
		const submitBtn = document.getElementById('startQuizBtn');
		const evaluationSelect = document.getElementById('evaluationSelect');
		const firstName = document.getElementById('firstName');
		const lastName = document.getElementById('lastName');

		function checkFormValidity() {
			if (evaluationSelect.value && firstName.value.trim() !== "" && lastName.value.trim() !== "") {
				submitBtn.disabled = false;
			} else {
				submitBtn.disabled = true;
			}
		}

		evaluationSelect.addEventListener('change', checkFormValidity);
		firstName.addEventListener('input', checkFormValidity);
		lastName.addEventListener('input', checkFormValidity);
	});