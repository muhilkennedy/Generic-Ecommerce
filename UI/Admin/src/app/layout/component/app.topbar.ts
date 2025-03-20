import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StyleClassModule } from 'primeng/styleclass';
import { AppConfigurator } from './app.configurator';
import { LayoutService } from '../service/layout.service';
import { MenuModule } from 'primeng/menu';
import { CookieService } from 'ngx-cookie-service';

@Component({
    selector: 'app-topbar',
    standalone: true,
    imports: [RouterModule, CommonModule, StyleClassModule, AppConfigurator, MenuModule],
    template: ` <div class="layout-topbar">
        <div class="layout-topbar-logo-container">
            <button class="layout-menu-button layout-topbar-action" (click)="layoutService.onMenuToggle()">
                <i class="pi pi-bars"></i>
            </button>
            <a class="layout-topbar-logo" routerLink="/">
                <!-- <svg viewBox="0 0 54 40" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                        fill-rule="evenodd"
                        clip-rule="evenodd"
                        d="M17.1637 19.2467C17.1566 19.4033 17.1529 19.561 17.1529 19.7194C17.1529 25.3503 21.7203 29.915 27.3546 29.915C32.9887 29.915 37.5561 25.3503 37.5561 19.7194C37.5561 19.5572 37.5524 19.3959 37.5449 19.2355C38.5617 19.0801 39.5759 18.9013 40.5867 18.6994L40.6926 18.6782C40.7191 19.0218 40.7326 19.369 40.7326 19.7194C40.7326 27.1036 34.743 33.0896 27.3546 33.0896C19.966 33.0896 13.9765 27.1036 13.9765 19.7194C13.9765 19.374 13.9896 19.0316 14.0154 18.6927L14.0486 18.6994C15.0837 18.9062 16.1223 19.0886 17.1637 19.2467ZM33.3284 11.4538C31.6493 10.2396 29.5855 9.52381 27.3546 9.52381C25.1195 9.52381 23.0524 10.2421 21.3717 11.4603C20.0078 11.3232 18.6475 11.1387 17.2933 10.907C19.7453 8.11308 23.3438 6.34921 27.3546 6.34921C31.36 6.34921 34.9543 8.10844 37.4061 10.896C36.0521 11.1292 34.692 11.3152 33.3284 11.4538ZM43.826 18.0518C43.881 18.6003 43.9091 19.1566 43.9091 19.7194C43.9091 28.8568 36.4973 36.2642 27.3546 36.2642C18.2117 36.2642 10.8 28.8568 10.8 19.7194C10.8 19.1615 10.8276 18.61 10.8816 18.0663L7.75383 17.4411C7.66775 18.1886 7.62354 18.9488 7.62354 19.7194C7.62354 30.6102 16.4574 39.4388 27.3546 39.4388C38.2517 39.4388 47.0855 30.6102 47.0855 19.7194C47.0855 18.9439 47.0407 18.1789 46.9536 17.4267L43.826 18.0518ZM44.2613 9.54743L40.9084 10.2176C37.9134 5.95821 32.9593 3.1746 27.3546 3.1746C21.7442 3.1746 16.7856 5.96385 13.7915 10.2305L10.4399 9.56057C13.892 3.83178 20.1756 0 27.3546 0C34.5281 0 40.8075 3.82591 44.2613 9.54743Z"
                        fill="var(--primary-color)"
                    />
                    <mask id="mask0_1413_1551" style="mask-type: alpha" maskUnits="userSpaceOnUse" x="0" y="8" width="54" height="11">
                        <path d="M27 18.3652C10.5114 19.1944 0 8.88892 0 8.88892C0 8.88892 16.5176 14.5866 27 14.5866C37.4824 14.5866 54 8.88892 54 8.88892C54 8.88892 43.4886 17.5361 27 18.3652Z" fill="var(--primary-color)" />
                    </mask>
                    <g mask="url(#mask0_1413_1551)">
                        <path
                            d="M-4.673e-05 8.88887L3.73084 -1.91434L-8.00806 17.0473L-4.673e-05 8.88887ZM27 18.3652L26.4253 6.95109L27 18.3652ZM54 8.88887L61.2673 17.7127L50.2691 -1.91434L54 8.88887ZM-4.673e-05 8.88887C-8.00806 17.0473 -8.00469 17.0505 -8.00132 17.0538C-8.00018 17.055 -7.99675 17.0583 -7.9944 17.0607C-7.98963 17.0653 -7.98474 17.0701 -7.97966 17.075C-7.96949 17.0849 -7.95863 17.0955 -7.94707 17.1066C-7.92401 17.129 -7.89809 17.1539 -7.86944 17.1812C-7.8122 17.236 -7.74377 17.3005 -7.66436 17.3743C-7.50567 17.5218 -7.30269 17.7063 -7.05645 17.9221C-6.56467 18.3532 -5.89662 18.9125 -5.06089 19.5534C-3.39603 20.83 -1.02575 22.4605 1.98012 24.0457C7.97874 27.2091 16.7723 30.3226 27.5746 29.7793L26.4253 6.95109C20.7391 7.23699 16.0326 5.61231 12.6534 3.83024C10.9703 2.94267 9.68222 2.04866 8.86091 1.41888C8.45356 1.10653 8.17155 0.867278 8.0241 0.738027C7.95072 0.673671 7.91178 0.637576 7.90841 0.634492C7.90682 0.63298 7.91419 0.639805 7.93071 0.65557C7.93897 0.663455 7.94952 0.673589 7.96235 0.686039C7.96883 0.692262 7.97582 0.699075 7.98338 0.706471C7.98719 0.710167 7.99113 0.714014 7.99526 0.718014C7.99729 0.720008 8.00047 0.723119 8.00148 0.724116C8.00466 0.727265 8.00796 0.730446 -4.673e-05 8.88887ZM27.5746 29.7793C37.6904 29.2706 45.9416 26.3684 51.6602 23.6054C54.5296 22.2191 56.8064 20.8465 58.4186 19.7784C59.2265 19.2431 59.873 18.7805 60.3494 18.4257C60.5878 18.2482 60.7841 18.0971 60.9374 17.977C61.014 17.9169 61.0799 17.8645 61.1349 17.8203C61.1624 17.7981 61.1872 17.7781 61.2093 17.7602C61.2203 17.7512 61.2307 17.7427 61.2403 17.7348C61.2452 17.7308 61.2499 17.727 61.2544 17.7233C61.2566 17.7215 61.2598 17.7188 61.261 17.7179C61.2642 17.7153 61.2673 17.7127 54 8.88887C46.7326 0.0650536 46.7357 0.0625219 46.7387 0.0600241C46.7397 0.0592345 46.7427 0.0567658 46.7446 0.0551857C46.7485 0.0520238 46.7521 0.0489887 46.7557 0.0460799C46.7628 0.0402623 46.7694 0.0349487 46.7753 0.0301318C46.7871 0.0204986 46.7966 0.0128495 46.8037 0.00712562C46.818 -0.00431848 46.8228 -0.00808311 46.8184 -0.00463784C46.8096 0.00228345 46.764 0.0378652 46.6828 0.0983779C46.5199 0.219675 46.2165 0.439161 45.7812 0.727519C44.9072 1.30663 43.5257 2.14765 41.7061 3.02677C38.0469 4.79468 32.7981 6.63058 26.4253 6.95109L27.5746 29.7793ZM54 8.88887C50.2691 -1.91433 50.27 -1.91467 50.271 -1.91498C50.2712 -1.91506 50.272 -1.91535 50.2724 -1.9155C50.2733 -1.91581 50.274 -1.91602 50.2743 -1.91616C50.2752 -1.91643 50.275 -1.91636 50.2738 -1.91595C50.2714 -1.91515 50.2652 -1.91302 50.2552 -1.9096C50.2351 -1.90276 50.1999 -1.89078 50.1503 -1.874C50.0509 -1.84043 49.8938 -1.78773 49.6844 -1.71863C49.2652 -1.58031 48.6387 -1.377 47.8481 -1.13035C46.2609 -0.635237 44.0427 0.0249875 41.5325 0.6823C36.215 2.07471 30.6736 3.15796 27 3.15796V26.0151C33.8087 26.0151 41.7672 24.2495 47.3292 22.7931C50.2586 22.026 52.825 21.2618 54.6625 20.6886C55.5842 20.4011 56.33 20.1593 56.8551 19.986C57.1178 19.8993 57.3258 19.8296 57.4735 19.7797C57.5474 19.7548 57.6062 19.7348 57.6493 19.72C57.6709 19.7127 57.6885 19.7066 57.7021 19.7019C57.7089 19.6996 57.7147 19.6976 57.7195 19.696C57.7219 19.6952 57.7241 19.6944 57.726 19.6938C57.7269 19.6934 57.7281 19.693 57.7286 19.6929C57.7298 19.6924 57.7309 19.692 54 8.88887ZM27 3.15796C23.3263 3.15796 17.7849 2.07471 12.4674 0.6823C9.95717 0.0249875 7.73904 -0.635237 6.15184 -1.13035C5.36118 -1.377 4.73467 -1.58031 4.3155 -1.71863C4.10609 -1.78773 3.94899 -1.84043 3.84961 -1.874C3.79994 -1.89078 3.76474 -1.90276 3.74471 -1.9096C3.73469 -1.91302 3.72848 -1.91515 3.72613 -1.91595C3.72496 -1.91636 3.72476 -1.91643 3.72554 -1.91616C3.72593 -1.91602 3.72657 -1.91581 3.72745 -1.9155C3.72789 -1.91535 3.72874 -1.91506 3.72896 -1.91498C3.72987 -1.91467 3.73084 -1.91433 -4.673e-05 8.88887C-3.73093 19.692 -3.72983 19.6924 -3.72868 19.6929C-3.72821 19.693 -3.72698 19.6934 -3.72603 19.6938C-3.72415 19.6944 -3.72201 19.6952 -3.71961 19.696C-3.71482 19.6976 -3.70901 19.6996 -3.7022 19.7019C-3.68858 19.7066 -3.67095 19.7127 -3.6494 19.72C-3.60629 19.7348 -3.54745 19.7548 -3.47359 19.7797C-3.32589 19.8296 -3.11788 19.8993 -2.85516 19.986C-2.33008 20.1593 -1.58425 20.4011 -0.662589 20.6886C1.17485 21.2618 3.74125 22.026 6.67073 22.7931C12.2327 24.2495 20.1913 26.0151 27 26.0151V3.15796Z"
                            fill="var(--primary-color)"
                        />
                    </g>
                </svg> -->
                <svg baseProfile="tiny" height="50" version="1.2" width="200" xmlns="http://www.w3.org/2000/svg" xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xlink="http://www.w3.org/1999/xlink"><defs /><image height="100%" width="100%" x="0" xlink:href="data:image/png;base64,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" y="0" />
                </svg>
            </a>
        </div>

        <div class="layout-topbar-actions">
            <div class="layout-config-menu">
                <button type="button" class="layout-topbar-action" (click)="toggleDarkMode()">
                    <i [ngClass]="{ 'pi ': true, 'pi-moon': layoutService.isDarkTheme(), 'pi-sun': !layoutService.isDarkTheme() }"></i>
                </button>
                <div class="relative">
                    <button
                        class="layout-topbar-action layout-topbar-action-highlight"
                        pStyleClass="@next"
                        enterFromClass="hidden"
                        enterActiveClass="animate-scalein"
                        leaveToClass="hidden"
                        leaveActiveClass="animate-fadeout"
                        [hideOnOutsideClick]="true"
                    >
                        <i class="pi pi-palette"></i>
                    </button>
                    <app-configurator />
                </div>
            </div>

            <button class="layout-topbar-menu-button layout-topbar-action" pStyleClass="@next" enterFromClass="hidden" enterActiveClass="animate-scalein" leaveToClass="hidden" leaveActiveClass="animate-fadeout" [hideOnOutsideClick]="true">
                <i class="pi pi-ellipsis-v"></i>
            </button>

            <div class="layout-topbar-menu hidden lg:block">
                <div class="layout-topbar-menu-content">
                    <!-- <button type="button" class="layout-topbar-action">
                        <i class="pi pi-user"></i>
                        <span>Profile</span>
                    </button> -->
                    <p-menu #menu [popup]="true" [model]="overlayMenuItems"></p-menu> 
                    <button type="button" class="layout-topbar-action" label="Options" (click)="menu.toggle($event)" style="width:auto">
                        <i class="pi pi-user"></i>
                    </button>
                </div>
            </div>

            <button (click)="logout()" > logout</button>
        </div>
    </div>`
})
export class AppTopbar {
    items!: MenuItem[];
    overlayMenuItems = [
        {
            label: 'Profile',
            icon: 'pi pi-id-card'
        },
        {
            label: 'Preferences',
            icon: 'pi pi-sliders-v'
        },
        {
            separator: true
        },
        {
            label: 'Logout',
            icon: 'pi pi-sign-out'
        }
    ];

    constructor(public layoutService: LayoutService, private cookieService: CookieService, private router: Router) {}

    toggleDarkMode() {
        this.layoutService.layoutConfig.update((state) => ({ ...state, darkTheme: !state.darkTheme }));
    }

    logout(){
        this.cookieService.deleteAll();
        this.router.navigate(['/login']);
    }
}
