let form = document.getElementById('registrationForm');
let repeatInput = document.getElementById('floatingRepeat');
let loginInput = document.getElementById('floatingUsername');

(function () {
  'use strict'

  let forms = document.querySelectorAll('.needs-validation')

  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
})()

loginInput.addEventListener('change', function (event){
  let postData = {};
  postData['username'] = loginInput.value;
  fetch('/unique_username', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(postData)
  })
  .then(response => {
    if (response.status == 200) {
      loginInput.setCustomValidity("Логин уже занят!");
    } else if (response.status == 204) {
      loginInput.setCustomValidity("");
    }
  })
  .catch(error => console.error('Error:', error));
});

repeatInput.addEventListener('input', function (event) {
  if (repeatInput.value != document.getElementById('floatingPassword').value) {
    repeatInput.setCustomValidity("Пароли не совпадают!");
  } else {
    repeatInput.setCustomValidity("");
  } 
});

form.addEventListener('submit', function (event) {

  event.preventDefault();

  let user = {
    name: document.getElementById('floatingName').value,
    surname: document.getElementById('floatingSurname').value,
    username: document.getElementById('floatingUsername').value,
    age: document.getElementById('floatingAge').value,
    email: document.getElementById('floatingEmail').value,
    role: 'USER'
  };

  let password = document.getElementById('floatingPassword').value;
  let repeat = document.getElementById('floatingRepeat').value;

  if (password.length >= 8 && password === repeat && form.checkValidity()) {
    user['password'] = password;

    fetch('/sign_up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    })
      .catch((error) => {
        console.error('Error:', error);
      });
    location.href = '/login';
  }
});