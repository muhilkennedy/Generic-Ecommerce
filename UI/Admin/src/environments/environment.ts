import firebaseConfig from '../assets/app/configuration/firebase-config.json';

export const environment = {
  production: false,
  tenantId: 'devTenant',
  apiUrl: 'http://localhost:8081',
  firebase: firebaseConfig,
  recaptchaUrl: "https://www.google.com/recaptcha/api.js",
  recaptchaSiteKey: "6LfwiR4rAAAAAL_1cZixzf2lS9MGLCfSO8ij7W-g"
};