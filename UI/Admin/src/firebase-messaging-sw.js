// This is the service worker file for Firebase Cloud Messaging.

// // <reference lib="webworker" />
// importScripts('https://www.gstatic.com/firebasejs/10.8.0/firebase-app-compat.js');
// importScripts('https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging-compat.js');

// firebase.initializeApp({
//     //firebase config
// });

// const messaging = firebase.messaging();

self.addEventListener('install', event => {
    event.waitUntil(
      fetch('/assets/app/configuration/firebase-config.json')
        .then(res => res.json())
        .then(config => {
          importScripts('https://www.gstatic.com/firebasejs/10.7.1/firebase-app-compat.js');
          importScripts('https://www.gstatic.com/firebasejs/10.7.1/firebase-messaging-compat.js');
          firebase.initializeApp(config);
          firebase.messaging();
        })
    );
  });