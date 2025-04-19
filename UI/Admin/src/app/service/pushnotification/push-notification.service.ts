import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { getToken, onMessage } from 'firebase/messaging';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Messaging } from '@angular/fire/messaging';
import { CommonUtil } from '../../util/CommonUtil.service';
import { ToastMessageService } from '../toastmessage/toast-message.service';
import { EmployeeDataService } from '../shared/employee/employee-data.service';

@Injectable({
  providedIn: 'root'
})
export class PushNotificationService {

  currentMessage = new BehaviorSubject<any>(null);

  constructor(private http: HttpClient, private messaging: Messaging, private toastMessage: ToastMessageService,private userData: EmployeeDataService) { }

  requestPermission() {
    Notification.requestPermission().then((permission) => {
      if (permission === 'granted') {
        getToken(this.messaging, {
          vapidKey: environment.firebase.vapidKey
        }).then((token) => {
          console.log('FCM Token:', token);
          localStorage.setItem(CommonUtil.KEY_FCM_TOKEN, token);
          this.subscribeToEmployeeTopic();
        }).catch((err) => {
          console.error('Error getting token:', err);
          this.toastMessage.showErrorMessage('Push Notification configuration failed! You can enable it in your browser settings.');
        });
      } else {
        console.warn('Push Notification permission not granted');
        this.toastMessage.showErrorMessage('Push Notification permission not granted! You can enable it in your browser settings.');
      }
    });
  }

  listenForMessages() {
    onMessage(this.messaging, (payload) => {
      console.log('Message received: ', payload);
      this.currentMessage.next(payload);
    });
  }

  subscribeToEmployeeTopic() {
    this.subscribeToTopic(CommonUtil.KEY_NOTIFICATION_TYPE_EMPLOYEE);
  }

  subscribeToTopic(topic: string) {
    this.http.post(`${environment.apiUrl}/tm/notification/push/subscribe`, {
      topic: topic,
      token: localStorage.getItem(CommonUtil.KEY_FCM_TOKEN),
      deviceInfo: navigator.userAgent,
      userid: this.userData.employee.rootid
    }).subscribe({
      next: (response: any) => {
        console.log('Subscribed to topic:', response);
      },
      error: (error) => {
        console.error('Error subscribing to topic:', error);
        this.toastMessage.showErrorMessage('Failed to subscribe to push notification topic.');
      }
    })
  }

}
