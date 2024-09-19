document.getElementById('dropdownMenuButton').addEventListener('click', function () {
    let list = document.getElementById('list').classList;
    if (list.contains('show')) {
        list.remove('show');
    } else {
        list.add('show');
    }
});