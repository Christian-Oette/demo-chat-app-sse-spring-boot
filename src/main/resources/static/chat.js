"use strict";

async function postData(url, data) {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    cache: 'no-cache',
    credentials: 'same-origin',
    headers: {
      'Content-Type': 'application/json'
    },
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: JSON.stringify(data)
  });
  return response;
}

function send() {
    const input = document.getElementById('messageInput').value;
    postData('/message',{ message: input, userName: window.assignedName, colorHex: window.assignedColor});
}

function handleWelcomeEvent(eventData) {
    window.assignedName = eventData.userName;
    window.assignedColor = eventData.colorHex;
    const span = document.getElementById('name')
    span.innerHTML = eventData.userName;
    const colorStyle = `color:#${eventData.colorHex};`
    span.setAttribute("style",colorStyle);
}

function handleChatEvent(eventData) {
    const userNameNode = document.createElement('span');
    userNameNode.innerHTML = eventData.userName + ': ';
    const colorStyle = `color:#${eventData.colorHex};`
    userNameNode.setAttribute("style", colorStyle);

    const li = document.createElement("li");
    li.appendChild(userNameNode);
    li.appendChild(document.createTextNode(eventData.message));

    const ul = document.getElementById("list");
    ul.appendChild(li);
}

function registerSSE(url) {
    const source = new EventSource(url);
    source.addEventListener('chat', event => {
        handleChatEvent(JSON.parse(event.data));
    })
    source.addEventListener('welcome', event => {
        handleWelcomeEvent(JSON.parse(event.data));
    })
    source.onopen = event => console.log("Connection opened");
    source.onerror = event => console.error("Connection opened");
    return source;
}

window.assignedName = "Unknown";
window.assignedColor = "000000";
window.eventSource = registerSSE('/register-client')

