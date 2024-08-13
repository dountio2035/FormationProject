let tasks = []; 

const div = document.querySelector("#tasks-html");
const inputText = document.getElementById("input-text");
const inputSubmit = document.getElementById("input-submit");
let ul = document.createElement('ul');
let title = '';
let updated = false;
let buttonName = 'Add ';
let updateId = 0;
div.appendChild(ul);
inputSubmit.querySelector('span').textContent = buttonName;

window.addEventListener('DOMContentLoaded', (e) => {
    displayAllTask();
}, true);

function buildTask(task) {
    let li = document.createElement('li');
    li.className = "tache d-flex justify-content-between align-items-center";
    
    li.innerHTML = `
        <span>#${task.id} - ${task.title}</span>
        <div>
            <i class="fas fa-edit text-primary me-2 icone-modifier" style="cursor:pointer;"></i>
            <i class="fas fa-trash text-danger icone-supprimer" style="cursor:pointer;"></i>
        </div>
    `;
    
    const editIcon = li.querySelector('.icone-modifier');
    const deleteIcon = li.querySelector('.icone-supprimer');

    editIcon.addEventListener('click', () => {
        inputText.value = task.title;
        updated = true;
        buttonName = 'Update ';
        inputSubmit.querySelector('span').textContent = buttonName;
        updateId = task.id - 1;

        deleteIcon.style.pointerEvents = 'none';
        deleteIcon.style.color = 'grey';
    });

    deleteIcon.addEventListener('click', () => {
        if (!updated) {
            tasks = tasks.filter(t => t.id !== task.id);
            ul.innerHTML = ''; 
            displayAllTask();
        }
    });

    ul.appendChild(li);
}

function displayAllTask() {
    ul.innerHTML = ''; 
    tasks.forEach(task => {
        buildTask(task);
    });
}

inputText.addEventListener('input', (e) => {
    title = e.target.value;  
});

inputSubmit.addEventListener('click', (e) => {
    e.preventDefault();  
    if (title.trim().length < 1) {
        return;
    }
    
    let lastTask;
    if (tasks.length > 0) {
        lastTask = tasks[tasks.length - 1];
    }
    let newTask = {id: 1, title: title};

    if (updated) {
        tasks[updateId].title = title;
        ul.innerHTML = ''; 
        displayAllTask();
        updated = false;
        buttonName = 'Add ';
        inputSubmit.querySelector('span').textContent = buttonName;
        inputText.value = '';
        title = '';  
        return;
    }

    if (lastTask === undefined) {
        tasks.push(newTask);
        buildTask(newTask);
    } else {
        newTask.id = lastTask.id + 1;
        tasks.push(newTask);
        buildTask(newTask);
    }

    inputText.value = ''; 
    title = '';  
    console.log(tasks);
});

displayAllTask();
