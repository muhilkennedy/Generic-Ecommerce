import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../layout/component/app.floatingconfigurator';

@Component({
    selector: 'app-error',
    imports: [ButtonModule, RippleModule, RouterModule, AppFloatingConfigurator, ButtonModule],
    standalone: true,
    template: ` <app-floating-configurator />
        <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] overflow-hidden">
            <div class="flex flex-col items-center justify-center">
                <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, rgba(233, 30, 99, 0.4) 10%, rgba(33, 150, 243, 0) 30%)">
                    <div class="w-full bg-surface-0 dark:bg-surface-900 py-20 px-8 sm:px-20 flex flex-col items-center" style="border-radius: 53px">
                        <div class="gap-4 flex flex-col items-center">
                            <div class="flex justify-center items-center border-2 border-pink-500 rounded-full" style="height: 3.2rem; width: 3.2rem">
                                <i class="pi pi-fw pi-exclamation-circle !text-2xl text-pink-500"></i>
                            </div>
                            <h1 class="text-surface-900 dark:text-surface-0 font-bold text-5xl mb-2">Error Occured</h1>
                            <span class="text-muted-color mb-8">Requested resource is not available.</span>
                            <svg width="442" height="240" viewBox="0 0 442 240" fill="none" xmlns="http://www.w3.org/2000/svg">
<g clip-path="url(#clip0_1403_1652)">
<path d="M63.8232 207.705H375.823V188.896C375.823 184.968 371.089 181.102 362.835 178.288L362.751 178.259C357.178 176.293 321.555 162.302 302.286 162.725C292.108 162.948 283.394 164.673 276.386 167.853C254.574 177.754 225.921 181.412 210.783 178.539C197.442 176.006 160.818 171.817 147.285 172.006C128.333 172.267 128.277 175.433 108.823 185.652C101.393 189.551 98.6139 190.041 63.8232 193.152V207.705Z" fill="#F2F2F2"/>
<path d="M13.524 180.059C12.8446 169.969 17.1283 159.775 26.0046 156.03C24.2102 166.426 26.421 177.979 32.1215 187.994C34.3418 191.895 37.1931 196.06 36.5863 200.136C36.2088 202.672 34.4835 204.685 32.3752 205.996C30.2669 207.307 27.7826 208.007 25.3286 208.694L24.9049 209.139C19.0726 200.106 14.2034 190.15 13.524 180.059Z" fill="#F2F2F2"/>
<path d="M26.1355 156.215C21.7335 162.992 19.7778 171.069 20.592 179.109C20.7267 180.946 21.138 182.752 21.8121 184.465C22.5493 186.182 23.6799 187.701 25.1126 188.901C26.4387 190.056 27.91 191.102 28.972 192.52C29.4961 193.212 29.8753 194.002 30.087 194.843C30.2986 195.685 30.3384 196.561 30.2039 197.418C29.8943 199.449 28.8428 201.146 27.7506 202.761C26.538 204.554 25.2467 206.393 25.0267 208.7C25.0001 208.98 24.5533 208.861 24.5799 208.582C24.9626 204.568 28.3475 202.128 29.4852 198.473C29.7946 197.589 29.9001 196.648 29.7938 195.718C29.6876 194.788 29.3723 193.895 28.8716 193.104C27.8821 191.605 26.3657 190.524 25.0143 189.365C23.5993 188.209 22.4484 186.764 21.6391 185.126C20.8829 183.441 20.4037 181.645 20.2201 179.808C19.7611 175.845 19.9492 171.835 20.7768 167.933C21.6621 163.639 23.3599 159.553 25.7788 155.896C25.9272 155.673 26.2829 155.993 26.1355 156.215V156.215Z" fill="white"/>
<path d="M20.1952 176.408C18.735 176.122 17.4003 175.388 16.3767 174.309C15.3531 173.229 14.6916 171.857 14.4841 170.383C14.452 170.097 14.8982 170.096 14.9303 170.383C15.1212 171.755 15.7374 173.033 16.6921 174.036C17.6469 175.04 18.8922 175.719 20.253 175.978C20.2816 175.982 20.3092 175.991 20.3342 176.005C20.3592 176.019 20.3812 176.038 20.3988 176.061C20.4164 176.084 20.4294 176.11 20.4368 176.137C20.4443 176.165 20.4462 176.194 20.4424 176.223C20.4385 176.251 20.429 176.279 20.4145 176.303C20.4 176.328 20.3806 176.35 20.3576 176.367C20.3346 176.384 20.3084 176.397 20.2805 176.404C20.2527 176.411 20.2236 176.413 20.1952 176.408Z" fill="white"/>
<path d="M24.3737 188.393C25.4633 187.581 26.3685 186.547 27.0299 185.36C27.6913 184.173 28.0938 182.86 28.2109 181.506C28.2386 181.227 28.6854 181.346 28.6577 181.624C28.533 183.033 28.1112 184.399 27.4202 185.632C26.7292 186.865 25.7847 187.938 24.6488 188.78C24.4315 188.939 24.1576 188.551 24.3737 188.393Z" fill="white"/>
<path d="M22.1824 163.969C22.6916 164.188 23.2484 164.273 23.7997 164.216C24.3511 164.16 24.8788 163.963 25.3329 163.645C25.5476 163.483 25.8213 163.872 25.608 164.033C25.1049 164.382 24.522 164.598 23.9132 164.662C23.3044 164.726 22.6892 164.636 22.1246 164.399C22.066 164.382 22.0149 164.346 21.9796 164.296C21.9442 164.246 21.9268 164.186 21.9301 164.125C21.9342 164.097 21.9444 164.07 21.9599 164.047C21.9754 164.023 21.9959 164.003 22.02 163.988C22.044 163.974 22.071 163.964 22.0991 163.961C22.1271 163.958 22.1556 163.96 22.1824 163.969H22.1824Z" fill="white"/>
<path d="M58.3897 172.47C58.259 172.576 58.1284 172.681 57.9983 172.79C56.2533 174.214 54.6452 175.797 53.195 177.52C53.0805 177.651 52.9667 177.786 52.8568 177.92C49.3873 182.166 46.8476 187.094 45.4022 192.383C44.8093 194.54 44.3932 196.742 44.158 198.966C43.8277 202.069 43.725 205.493 42.3922 208.085C42.257 208.356 42.1029 208.618 41.931 208.867L25.5646 209.698C25.5238 209.681 25.4836 209.668 25.4426 209.651L24.7949 209.714C24.7989 209.596 24.8056 209.475 24.8096 209.357C24.8116 209.288 24.8172 209.22 24.8192 209.151C24.8217 209.106 24.8244 209.06 24.824 209.018C24.8247 209.003 24.8257 208.988 24.8272 208.976C24.8267 208.935 24.8309 208.896 24.8311 208.858C24.8667 208.177 24.9059 207.496 24.9528 206.814C24.9521 206.811 24.9521 206.811 24.955 206.807C25.2363 201.681 26.1669 196.612 27.7246 191.72C27.7727 191.578 27.82 191.431 27.8756 191.288C28.602 189.168 29.5214 187.119 30.6221 185.167C31.2235 184.111 31.8856 183.09 32.6049 182.11C34.4674 179.586 36.7711 177.419 39.4047 175.715C42.0929 173.925 45.1185 172.702 48.2959 172.122C51.4734 171.543 54.7358 171.618 57.883 172.344C58.0528 172.385 58.2191 172.426 58.3897 172.47Z" fill="#F2F2F2"/>
<path d="M58.393 172.681C50.7214 174.61 43.9947 179.223 39.4323 185.685C38.4307 187.061 37.6815 188.604 37.2196 190.242C36.8171 191.945 36.8828 193.725 37.4098 195.394C37.8715 197.016 38.5279 198.648 38.5949 200.299C38.6318 201.107 38.4769 201.912 38.1431 202.648C37.8092 203.385 37.3058 204.031 36.6739 204.536C35.1526 205.8 33.1818 206.377 31.225 206.873C29.0524 207.423 26.782 207.959 25.1706 209.473C24.9753 209.656 24.6536 209.303 24.8486 209.119C27.6523 206.485 32.1411 206.78 35.3914 204.856C36.908 203.958 38.0934 202.56 38.1474 200.656C38.1945 198.99 37.519 197.307 37.0376 195.666C36.5013 194.036 36.372 192.3 36.6611 190.609C37.0309 188.946 37.7144 187.368 38.6749 185.96C40.7581 182.799 43.3961 180.041 46.4614 177.82C50.0094 175.228 54.0183 173.334 58.2741 172.239C58.5421 172.169 58.6592 172.612 58.3931 172.681H58.393Z" fill="white"/>
<path d="M40.7415 183.519C38.4672 181.368 37.8981 178.056 39.3987 175.782C39.5466 175.558 39.941 175.826 39.793 176.051C38.3872 178.181 38.9307 181.237 41.0567 183.247C41.2804 183.459 40.9639 183.729 40.7415 183.519Z" fill="white"/>
<path d="M37.0697 194.587C38.45 194.691 39.8372 194.514 41.147 194.066C42.4568 193.618 43.6619 192.909 44.6892 191.981C44.8852 191.798 45.2069 192.152 45.0112 192.334C43.9398 193.297 42.6838 194.033 41.3195 194.496C39.9552 194.96 38.5109 195.141 37.0745 195.029C36.7849 195.011 36.7817 194.569 37.0697 194.587Z" fill="white"/>
<path d="M50.1378 175.837C50.4819 176.317 50.9274 176.715 51.4428 177.002C51.9581 177.29 52.5306 177.46 53.1194 177.502C53.4085 177.515 53.4114 177.957 53.1242 177.944C52.4738 177.896 51.8416 177.707 51.2715 177.39C50.7014 177.073 50.207 176.636 49.8226 176.109C49.7817 176.068 49.7585 176.012 49.7578 175.954C49.7572 175.895 49.7792 175.839 49.8192 175.796C49.8675 175.761 49.9274 175.746 49.9866 175.753C50.0458 175.761 50.0999 175.791 50.1378 175.837Z" fill="white"/>
<path d="M423.283 180.059C423.962 169.969 419.679 159.775 410.802 156.03C412.597 166.426 410.386 177.979 404.685 187.994C402.465 191.895 399.614 196.06 400.221 200.136C400.598 202.672 402.323 204.685 404.432 205.996C406.54 207.307 409.024 208.007 411.478 208.694L411.902 209.139C417.734 200.106 422.604 190.15 423.283 180.059Z" fill="#F2F2F2"/>
<path d="M410.672 156.215C415.074 162.992 417.029 171.069 416.215 179.109C416.081 180.946 415.669 182.752 414.995 184.465C414.258 186.182 413.127 187.701 411.695 188.901C410.369 190.056 408.897 191.102 407.835 192.52C407.311 193.212 406.932 194.002 406.72 194.843C406.509 195.685 406.469 196.561 406.603 197.418C406.913 199.449 407.965 201.146 409.057 202.761C410.269 204.554 411.561 206.393 411.781 208.7C411.807 208.98 412.254 208.861 412.227 208.582C411.845 204.568 408.46 202.128 407.322 198.473C407.013 197.589 406.907 196.648 407.013 195.718C407.12 194.788 407.435 193.895 407.936 193.104C408.925 191.605 410.442 190.524 411.793 189.365C413.208 188.209 414.359 186.764 415.168 185.126C415.924 183.441 416.404 181.645 416.587 179.808C417.046 175.845 416.858 171.835 416.03 167.933C415.145 163.639 413.447 159.553 411.028 155.896C410.88 155.673 410.524 155.993 410.672 156.215V156.215Z" fill="white"/>
<path d="M416.611 176.408C418.072 176.122 419.406 175.388 420.43 174.309C421.453 173.229 422.115 171.857 422.323 170.383C422.355 170.097 421.908 170.096 421.876 170.383C421.685 171.755 421.069 173.033 420.114 174.036C419.16 175.04 417.914 175.719 416.554 175.978C416.525 175.982 416.497 175.991 416.472 176.005C416.447 176.019 416.425 176.038 416.408 176.061C416.39 176.084 416.377 176.11 416.37 176.137C416.362 176.165 416.36 176.194 416.364 176.223C416.368 176.251 416.378 176.279 416.392 176.303C416.407 176.328 416.426 176.35 416.449 176.367C416.472 176.384 416.498 176.397 416.526 176.404C416.554 176.411 416.583 176.413 416.611 176.408Z" fill="white"/>
<path d="M412.433 188.393C411.343 187.581 410.438 186.547 409.777 185.36C409.115 184.173 408.713 182.86 408.596 181.506C408.568 181.227 408.121 181.346 408.149 181.624C408.273 183.033 408.695 184.399 409.386 185.632C410.077 186.865 411.022 187.938 412.158 188.78C412.375 188.939 412.649 188.551 412.433 188.393Z" fill="white"/>
<path d="M414.624 163.969C414.115 164.188 413.558 164.273 413.007 164.216C412.455 164.16 411.927 163.963 411.473 163.645C411.259 163.483 410.985 163.872 411.198 164.033C411.701 164.382 412.284 164.598 412.893 164.662C413.502 164.726 414.117 164.636 414.682 164.399C414.74 164.382 414.791 164.346 414.827 164.296C414.862 164.246 414.88 164.186 414.876 164.125C414.872 164.097 414.862 164.07 414.846 164.047C414.831 164.023 414.81 164.003 414.786 163.988C414.762 163.974 414.735 163.964 414.707 163.961C414.679 163.958 414.651 163.96 414.624 163.969H414.624Z" fill="white"/>
<path d="M378.417 172.471C378.548 172.576 378.678 172.681 378.808 172.79C380.553 174.214 382.161 175.797 383.612 177.52C383.726 177.651 383.84 177.786 383.95 177.92C387.419 182.167 389.959 187.094 391.404 192.383C391.997 194.54 392.413 196.742 392.649 198.966C392.979 202.069 393.082 205.493 394.414 208.085C394.55 208.356 394.704 208.618 394.876 208.867L411.242 209.698C411.283 209.681 411.323 209.668 411.364 209.651L412.012 209.714C412.008 209.596 412.001 209.475 411.997 209.357C411.995 209.288 411.989 209.22 411.987 209.151C411.985 209.106 411.982 209.06 411.983 209.018C411.982 209.003 411.981 208.988 411.979 208.976C411.98 208.935 411.976 208.896 411.975 208.858C411.94 208.177 411.901 207.496 411.854 206.814C411.855 206.811 411.855 206.811 411.852 206.807C411.57 201.681 410.64 196.612 409.082 191.72C409.034 191.578 408.987 191.431 408.931 191.288C408.205 189.168 407.285 187.119 406.185 185.167C405.583 184.111 404.921 183.09 404.202 182.11C402.339 179.586 400.036 177.419 397.402 175.715C394.714 173.925 391.688 172.702 388.511 172.122C385.333 171.543 382.071 171.618 378.924 172.344C378.754 172.385 378.588 172.426 378.417 172.471Z" fill="#F2F2F2"/>
<path d="M378.414 172.681C386.086 174.61 392.812 179.223 397.375 185.685C398.376 187.061 399.126 188.604 399.587 190.242C399.99 191.945 399.924 193.725 399.397 195.394C398.936 197.016 398.279 198.648 398.212 200.299C398.175 201.107 398.33 201.912 398.664 202.648C398.998 203.385 399.501 204.031 400.133 204.536C401.654 205.8 403.625 206.377 405.582 206.873C407.755 207.423 410.025 207.959 411.636 209.473C411.832 209.656 412.153 209.303 411.958 209.119C409.155 206.485 404.666 206.78 401.416 204.856C399.899 203.958 398.714 202.56 398.66 200.656C398.613 198.99 399.288 197.307 399.769 195.666C400.306 194.036 400.435 192.3 400.146 190.609C399.776 188.946 399.093 187.368 398.132 185.96C396.049 182.799 393.411 180.041 390.346 177.82C386.798 175.228 382.789 173.334 378.533 172.239C378.265 172.169 378.148 172.612 378.414 172.681Z" fill="white"/>
<path d="M396.066 183.519C398.34 181.368 398.909 178.056 397.408 175.782C397.26 175.558 396.866 175.826 397.014 176.051C398.42 178.181 397.876 181.237 395.75 183.247C395.527 183.459 395.843 183.729 396.066 183.519Z" fill="white"/>
<path d="M399.737 194.587C398.356 194.691 396.969 194.514 395.659 194.066C394.35 193.618 393.145 192.909 392.117 191.981C391.921 191.798 391.6 192.152 391.795 192.334C392.867 193.297 394.123 194.033 395.487 194.496C396.851 194.96 398.296 195.141 399.732 195.029C400.022 195.011 400.025 194.569 399.737 194.587Z" fill="white"/>
<path d="M386.669 175.837C386.325 176.317 385.879 176.715 385.364 177.002C384.849 177.29 384.276 177.46 383.687 177.502C383.398 177.515 383.395 177.957 383.683 177.944C384.333 177.896 384.965 177.707 385.535 177.39C386.105 177.073 386.6 176.636 386.984 176.109C387.025 176.068 387.048 176.012 387.049 175.954C387.05 175.895 387.028 175.839 386.987 175.796C386.939 175.761 386.879 175.746 386.82 175.753C386.761 175.761 386.707 175.791 386.669 175.837Z" fill="white"/>
<path d="M28.0342 130.104H25.0342V131.104H28.0342V130.104Z" fill="#E6E6E6"/>
<path d="M405.601 131.104H399.511V130.104H405.601V131.104ZM393.421 131.104H387.331V130.104H393.421V131.104ZM381.242 131.104H375.152V130.104H381.242V131.104ZM369.062 131.104H362.972V130.104H369.062V131.104ZM356.883 131.104H350.793V130.104H356.883V131.104ZM344.703 131.104H338.613V130.104H344.703V131.104ZM332.523 131.104H326.434V130.104H332.523V131.104ZM320.344 131.104H314.254V130.104H320.344L320.344 131.104ZM308.164 131.104H302.075V130.104H308.164V131.104ZM295.985 131.104H289.895V130.104H295.985V131.104ZM283.805 131.104H277.715V130.104H283.805V131.104ZM271.626 131.104H265.536V130.104H271.626V131.104ZM259.446 131.104H253.356V130.104H259.446V131.104ZM247.266 131.104H241.177V130.104H247.266V131.104ZM235.087 131.104H228.997V130.104H235.087V131.104ZM222.907 131.104H216.818V130.104H222.907V131.104ZM210.728 131.104H204.638V130.104H210.728V131.104ZM198.548 131.104H192.458V130.104H198.548V131.104ZM186.369 131.104H180.279V130.104H186.369V131.104ZM174.189 131.104H168.099V130.104H174.189V131.104ZM162.01 131.104H155.92V130.104H162.01V131.104ZM149.83 131.104H143.74V130.104H149.83V131.104ZM137.65 131.104H131.561V130.104H137.65V131.104ZM125.471 131.104H119.381V130.104H125.471L125.471 131.104ZM113.291 131.104H107.202V130.104H113.291V131.104ZM101.112 131.104H95.0219V130.104H101.112V131.104ZM88.932 131.104H82.8422V130.104H88.932V131.104ZM76.7526 131.104H70.6628V130.104H76.7526V131.104ZM64.5729 131.104H58.4831V130.104H64.5729V131.104ZM52.3933 131.104H46.3037V130.104H52.3933V131.104ZM40.2138 131.104H34.124V130.104H40.2138V131.104Z" fill="#E6E6E6"/>
<path d="M414.69 130.104H411.69V131.104H414.69V130.104Z" fill="#E6E6E6"/>
<path d="M28.0342 123.104H25.0342V124.104H28.0342V123.104Z" fill="#E6E6E6"/>
<path d="M405.601 124.104H399.511V123.104H405.601V124.104ZM393.421 124.104H387.331V123.104H393.421V124.104ZM381.242 124.104H375.152V123.104H381.242V124.104ZM369.062 124.104H362.972V123.104H369.062V124.104ZM356.883 124.104H350.793V123.104H356.883V124.104ZM344.703 124.104H338.613V123.104H344.703V124.104ZM332.523 124.104H326.434V123.104H332.523V124.104ZM320.344 124.104H314.254V123.104H320.344L320.344 124.104ZM308.164 124.104H302.075V123.104H308.164V124.104ZM295.985 124.104H289.895V123.104H295.985V124.104ZM283.805 124.104H277.715V123.104H283.805V124.104ZM271.626 124.104H265.536V123.104H271.626V124.104ZM259.446 124.104H253.356V123.104H259.446V124.104ZM247.266 124.104H241.177V123.104H247.266V124.104ZM235.087 124.104H228.997V123.104H235.087V124.104ZM222.907 124.104H216.818V123.104H222.907V124.104ZM210.728 124.104H204.638V123.104H210.728V124.104ZM198.548 124.104H192.458V123.104H198.548V124.104ZM186.369 124.104H180.279V123.104H186.369V124.104ZM174.189 124.104H168.099V123.104H174.189V124.104ZM162.01 124.104H155.92V123.104H162.01V124.104ZM149.83 124.104H143.74V123.104H149.83V124.104ZM137.65 124.104H131.561V123.104H137.65V124.104ZM125.471 124.104H119.381V123.104H125.471L125.471 124.104ZM113.291 124.104H107.202V123.104H113.291V124.104ZM101.112 124.104H95.0219V123.104H101.112V124.104ZM88.932 124.104H82.8422V123.104H88.932V124.104ZM76.7526 124.104H70.6628V123.104H76.7526V124.104ZM64.5729 124.104H58.4831V123.104H64.5729V124.104ZM52.3933 124.104H46.3037V123.104H52.3933V124.104ZM40.2138 124.104H34.124V123.104H40.2138V124.104Z" fill="#E6E6E6"/>
<path d="M414.69 123.104H411.69V124.104H414.69V123.104Z" fill="#E6E6E6"/>
<path d="M28.0342 108.604H25.0342V109.604H28.0342V108.604Z" fill="#E6E6E6"/>
<path d="M405.601 109.604H399.511V108.604H405.601V109.604ZM393.421 109.604H387.331V108.604H393.421V109.604ZM381.242 109.604H375.152V108.604H381.242V109.604ZM369.062 109.604H362.972V108.604H369.062V109.604ZM356.883 109.604H350.793V108.604H356.883V109.604ZM344.703 109.604H338.613V108.604H344.703V109.604ZM332.523 109.604H326.434V108.604H332.523V109.604ZM320.344 109.604H314.254V108.604H320.344L320.344 109.604ZM308.164 109.604H302.075V108.604H308.164V109.604ZM295.985 109.604H289.895V108.604H295.985V109.604ZM283.805 109.604H277.715V108.604H283.805V109.604ZM271.626 109.604H265.536V108.604H271.626V109.604ZM259.446 109.604H253.356V108.604H259.446V109.604ZM247.266 109.604H241.177V108.604H247.266V109.604ZM235.087 109.604H228.997V108.604H235.087V109.604ZM222.907 109.604H216.818V108.604H222.907V109.604ZM210.728 109.604H204.638V108.604H210.728V109.604ZM198.548 109.604H192.458V108.604H198.548V109.604ZM186.369 109.604H180.279V108.604H186.369V109.604ZM174.189 109.604H168.099V108.604H174.189V109.604ZM162.01 109.604H155.92V108.604H162.01V109.604ZM149.83 109.604H143.74V108.604H149.83V109.604ZM137.65 109.604H131.561V108.604H137.65V109.604ZM125.471 109.604H119.381V108.604H125.471L125.471 109.604ZM113.291 109.604H107.202V108.604H113.291V109.604ZM101.112 109.604H95.0219V108.604H101.112V109.604ZM88.932 109.604H82.8422V108.604H88.932V109.604ZM76.7526 109.604H70.6628V108.604H76.7526V109.604ZM64.5729 109.604H58.4831V108.604H64.5729V109.604ZM52.3933 109.604H46.3037V108.604H52.3933V109.604ZM40.2138 109.604H34.124V108.604H40.2138V109.604Z" fill="#E6E6E6"/>
<path d="M414.69 108.604H411.69V109.604H414.69V108.604Z" fill="#E6E6E6"/>
<path d="M28.0342 98.6045H25.0342V99.6045H28.0342V98.6045Z" fill="#E6E6E6"/>
<path d="M405.601 99.6045H399.511V98.6045H405.601V99.6045ZM393.421 99.6045H387.331V98.6045H393.421V99.6045ZM381.242 99.6045H375.152V98.6045H381.242V99.6045ZM369.062 99.6045H362.972V98.6045H369.062V99.6045ZM356.883 99.6045H350.793V98.6045H356.883V99.6045ZM344.703 99.6045H338.613V98.6045H344.703V99.6045ZM332.523 99.6045H326.434V98.6045H332.523V99.6045ZM320.344 99.6045H314.254V98.6045H320.344L320.344 99.6045ZM308.164 99.6045H302.075V98.6045H308.164V99.6045ZM295.985 99.6045H289.895V98.6045H295.985V99.6045ZM283.805 99.6045H277.715V98.6045H283.805V99.6045ZM271.626 99.6045H265.536V98.6045H271.626V99.6045ZM259.446 99.6045H253.356V98.6045H259.446V99.6045ZM247.266 99.6045H241.177V98.6045H247.266V99.6045ZM235.087 99.6045H228.997V98.6045H235.087V99.6045ZM222.907 99.6045H216.818V98.6045H222.907V99.6045ZM210.728 99.6045H204.638V98.6045H210.728V99.6045ZM198.548 99.6045H192.458V98.6045H198.548V99.6045ZM186.369 99.6045H180.279V98.6045H186.369V99.6045ZM174.189 99.6045H168.099V98.6045H174.189V99.6045ZM162.01 99.6045H155.92V98.6045H162.01V99.6045ZM149.83 99.6045H143.74V98.6045H149.83V99.6045ZM137.65 99.6045H131.561V98.6045H137.65V99.6045ZM125.471 99.6045H119.381V98.6045H125.471L125.471 99.6045ZM113.291 99.6045H107.202V98.6045H113.291V99.6045ZM101.112 99.6045H95.0219V98.6045H101.112V99.6045ZM88.932 99.6045H82.8422V98.6045H88.932V99.6045ZM76.7526 99.6045H70.6628V98.6045H76.7526V99.6045ZM64.5729 99.6045H58.4831V98.6045H64.5729V99.6045ZM52.3933 99.6045H46.3037V98.6045H52.3933V99.6045ZM40.2138 99.6045H34.124V98.6045H40.2138V99.6045Z" fill="#E6E6E6"/>
<path d="M414.69 98.6045H411.69V99.6045H414.69V98.6045Z" fill="#E6E6E6"/>
<path d="M28.0342 77.1045H25.0342V78.1045H28.0342V77.1045Z" fill="#E6E6E6"/>
<path d="M405.601 78.1045H399.511V77.1045H405.601V78.1045ZM393.421 78.1045H387.331V77.1045H393.421V78.1045ZM381.242 78.1045H375.152V77.1045H381.242V78.1045ZM369.062 78.1045H362.972V77.1045H369.062V78.1045ZM356.883 78.1045H350.793V77.1045H356.883V78.1045ZM344.703 78.1045H338.613V77.1045H344.703V78.1045ZM332.523 78.1045H326.434V77.1045H332.523V78.1045ZM320.344 78.1045H314.254V77.1045H320.344L320.344 78.1045ZM308.164 78.1045H302.075V77.1045H308.164V78.1045ZM295.985 78.1045H289.895V77.1045H295.985V78.1045ZM283.805 78.1045H277.715V77.1045H283.805V78.1045ZM271.626 78.1045H265.536V77.1045H271.626V78.1045ZM259.446 78.1045H253.356V77.1045H259.446V78.1045ZM247.266 78.1045H241.177V77.1045H247.266V78.1045ZM235.087 78.1045H228.997V77.1045H235.087V78.1045ZM222.907 78.1045H216.818V77.1045H222.907V78.1045ZM210.728 78.1045H204.638V77.1045H210.728V78.1045ZM198.548 78.1045H192.458V77.1045H198.548V78.1045ZM186.369 78.1045H180.279V77.1045H186.369V78.1045ZM174.189 78.1045H168.099V77.1045H174.189V78.1045ZM162.01 78.1045H155.92V77.1045H162.01V78.1045ZM149.83 78.1045H143.74V77.1045H149.83V78.1045ZM137.65 78.1045H131.561V77.1045H137.65V78.1045ZM125.471 78.1045H119.381V77.1045H125.471L125.471 78.1045ZM113.291 78.1045H107.202V77.1045H113.291V78.1045ZM101.112 78.1045H95.0219V77.1045H101.112V78.1045ZM88.932 78.1045H82.8422V77.1045H88.932V78.1045ZM76.7526 78.1045H70.6628V77.1045H76.7526V78.1045ZM64.5729 78.1045H58.4831V77.1045H64.5729V78.1045ZM52.3933 78.1045H46.3037V77.1045H52.3933V78.1045ZM40.2138 78.1045H34.124V77.1045H40.2138V78.1045Z" fill="#E6E6E6"/>
<path d="M414.69 77.1045H411.69V78.1045H414.69V77.1045Z" fill="#E6E6E6"/>
<path d="M170.487 48.3306C170.487 53.7136 168.891 58.9758 165.9 63.4516C162.91 67.9275 158.659 71.416 153.685 73.476C148.712 75.536 143.24 76.075 137.96 75.0248C132.68 73.9746 127.831 71.3824 124.024 67.576C120.218 63.7696 117.626 58.92 116.576 53.6404C115.526 48.3608 116.065 42.8883 118.125 37.915C120.185 32.9417 123.673 28.691 128.149 25.7003C132.625 22.7097 137.887 21.1134 143.27 21.1134C146.849 21.0975 150.395 21.7906 153.704 23.1528C157.014 24.5149 160.02 26.5191 162.551 29.0496C165.081 31.5802 167.086 34.5869 168.448 37.8962C169.81 41.2055 170.503 44.7519 170.487 48.3306Z" fill="#F2F2F2"/>
<path d="M374.769 84.0475C389.801 84.0475 401.986 71.8619 401.986 56.8303C401.986 41.7987 389.801 29.6132 374.769 29.6132C359.737 29.6132 347.552 41.7987 347.552 56.8303C347.552 71.8619 359.737 84.0475 374.769 84.0475Z" fill="#F2F2F2"/>
<path d="M324.987 27.8786C324.978 33.2616 323.372 38.5209 320.373 42.9915C317.375 47.462 313.118 50.9429 308.141 52.9941C303.164 55.0453 297.691 55.5746 292.413 54.5151C287.135 53.4555 282.29 50.8547 278.49 47.0416C274.691 43.2285 272.107 38.3743 271.066 33.0928C270.026 27.8113 270.574 22.3398 272.643 17.3702C274.712 12.4006 278.208 8.15603 282.689 5.17331C287.17 2.19059 292.435 0.603658 297.818 0.613202C301.395 0.610261 304.937 1.3142 308.241 2.68456C311.545 4.05493 314.546 6.06469 317.07 8.59837C319.595 11.132 321.594 14.1397 322.953 17.4484C324.311 20.7572 325.003 24.3018 324.987 27.8786Z" fill="#F2F2F2"/>
<path d="M222.77 99.0474C237.801 99.0474 249.987 86.8618 249.987 71.8302C249.987 56.7986 237.801 44.613 222.77 44.613C207.738 44.613 195.553 56.7986 195.553 71.8302C195.553 86.8618 207.738 99.0474 222.77 99.0474Z" fill="#F2F2F2"/>
<path d="M65.2699 111.547C80.3015 111.547 92.4871 99.3618 92.4871 84.3302C92.4871 69.2986 80.3015 57.113 65.2699 57.113C50.2383 57.113 38.0527 69.2986 38.0527 84.3302C38.0527 99.3618 50.2383 111.547 65.2699 111.547Z" fill="#F2F2F2"/>
<path d="M55.5527 97.2948H74.3709V91.5675H62.6607V87.0675H73.4504V81.3403H62.6607V76.8403H74.422V71.113H55.5527V97.2948Z" fill="#E91E63"/>
<path d="M132.053 61.2949H139.161V52.6529H141.615L146.217 61.2949H153.939L148.621 51.5279C151.408 50.1856 153.07 47.6415 153.07 44.0109C153.07 38.437 149.171 35.1132 143.354 35.1132H132.053V61.2949ZM139.161 47.1302V40.7893H141.666C144.159 40.7893 145.655 41.7609 145.655 44.0109C145.655 46.2481 144.159 47.1302 141.666 47.1302H139.161Z" fill="#E91E63"/>
<path d="M212.053 84.7948H219.161V76.1528H221.615L226.217 84.7948H233.939L228.621 75.0278C231.408 73.6855 233.07 71.1414 233.07 67.5108C233.07 61.9369 229.171 58.613 223.354 58.613H212.053V84.7948ZM219.161 70.6301V64.2892H221.666C224.159 64.2892 225.655 65.2608 225.655 67.5108C225.655 69.748 224.159 70.6301 221.666 70.6301H219.161Z" fill="#E91E63"/>
<path d="M364.052 69.7948H371.16V61.1528H373.614L378.217 69.7948H385.938L380.62 60.0278C383.407 58.6855 385.069 56.1414 385.069 52.5107C385.069 46.9369 381.17 43.613 375.353 43.613H364.052V69.7948ZM371.16 55.6301V49.2892H373.665C376.158 49.2892 377.654 50.2608 377.654 52.5107C377.654 54.748 376.158 55.6301 373.665 55.6301H371.16Z" fill="#E91E63"/>
<path d="M310.518 28.062C310.518 19.3177 304.893 14.6132 297.785 14.6132C290.626 14.6132 285.052 19.3177 285.052 28.062C285.052 36.7552 290.626 41.5108 297.785 41.5108C304.893 41.5108 310.518 36.8063 310.518 28.062ZM303.205 28.062C303.205 32.7665 301.313 35.3233 297.785 35.3233C294.256 35.3233 292.364 32.7665 292.364 28.062C292.364 23.3575 294.256 20.8006 297.785 20.8006C301.313 20.8006 303.205 23.3575 303.205 28.062Z" fill="#E91E63"/>
<path d="M57.0862 82.595C57.0699 82.5498 57.0551 82.5041 57.0391 82.4589C57.0073 82.4869 56.9769 82.5158 56.9424 82.5419L57.0862 82.595Z" fill="#2F2E41"/>
<path d="M65.2777 132.376H62.6797V208.527H65.2777V132.376Z" fill="#3F3D56"/>
<path d="M143.06 96.8104H140.462V208.527H143.06V96.8104Z" fill="#3F3D56"/>
<path d="M221.001 120.193H218.403V208.527H221.001V120.193Z" fill="#3F3D56"/>
<path d="M298.943 77.325H296.345V208.527H298.943V77.325Z" fill="#3F3D56"/>
<path d="M376.885 107.203H374.287V208.527H376.885V107.203Z" fill="#3F3D56"/>
<path d="M141.921 106.549C146.31 106.549 149.868 102.991 149.868 98.6011C149.868 94.2116 146.31 90.6532 141.921 90.6532C137.531 90.6532 133.973 94.2116 133.973 98.6011C133.973 102.991 137.531 106.549 141.921 106.549Z" fill="#E91E63"/>
<path d="M297.804 85.7645C302.194 85.7645 305.752 82.2061 305.752 77.8166C305.752 73.4272 302.194 69.8688 297.804 69.8688C293.415 69.8688 289.856 73.4272 289.856 77.8166C289.856 82.2061 293.415 85.7645 297.804 85.7645Z" fill="#E91E63"/>
<path d="M219.863 128.632C224.252 128.632 227.811 125.074 227.811 120.685C227.811 116.295 224.252 112.737 219.863 112.737C215.473 112.737 211.915 116.295 211.915 120.685C211.915 125.074 215.473 128.632 219.863 128.632Z" fill="#E91E63"/>
<path d="M63.9791 140.324C68.3686 140.324 71.927 136.765 71.927 132.376C71.927 127.986 68.3686 124.428 63.9791 124.428C59.5896 124.428 56.0312 127.986 56.0312 132.376C56.0312 136.765 59.5896 140.324 63.9791 140.324Z" fill="#E91E63"/>
<path d="M375.746 114.343C380.135 114.343 383.694 110.785 383.694 106.395C383.694 102.006 380.135 98.4474 375.746 98.4474C371.356 98.4474 367.798 102.006 367.798 106.395C367.798 110.785 371.356 114.343 375.746 114.343Z" fill="#E91E63"/>
<path d="M431.498 207.742H9.55162L6.90234 209.066C6.90236 209.418 7.04192 209.755 7.29034 210.003C7.53875 210.251 7.87567 210.391 8.22699 210.391H431.498C431.849 210.391 432.186 210.251 432.435 210.003C432.683 209.755 432.823 209.418 432.823 209.066C432.823 208.715 432.683 208.378 432.435 208.13C432.186 207.881 431.849 207.742 431.498 207.742Z" fill="#3F3D56"/>
<path d="M8.00061 216.387C12.3901 216.387 15.9485 212.829 15.9485 208.439C15.9485 204.05 12.3901 200.491 8.00061 200.491C3.61112 200.491 0.0527344 204.05 0.0527344 208.439C0.0527344 212.829 3.61112 216.387 8.00061 216.387Z" fill="#3F3D56"/>
<path d="M64.0006 239.387C68.3901 239.387 71.9485 235.829 71.9485 231.439C71.9485 227.05 68.3901 223.491 64.0006 223.491C59.6111 223.491 56.0527 227.05 56.0527 231.439C56.0527 235.829 59.6111 239.387 64.0006 239.387Z" fill="#E6E6E6"/>
<path d="M142.001 239.387C146.39 239.387 149.948 235.829 149.948 231.439C149.948 227.05 146.39 223.491 142.001 223.491C137.611 223.491 134.053 227.05 134.053 231.439C134.053 235.829 137.611 239.387 142.001 239.387Z" fill="#E6E6E6"/>
<path d="M220.001 239.387C224.39 239.387 227.948 235.829 227.948 231.439C227.948 227.05 224.39 223.491 220.001 223.491C215.611 223.491 212.053 227.05 212.053 231.439C212.053 235.829 215.611 239.387 220.001 239.387Z" fill="#E6E6E6"/>
<path d="M297.5 239.387C301.889 239.387 305.448 235.829 305.448 231.439C305.448 227.05 301.889 223.491 297.5 223.491C293.11 223.491 289.552 227.05 289.552 231.439C289.552 235.829 293.11 239.387 297.5 239.387Z" fill="#E6E6E6"/>
<path d="M375.5 239.387C379.889 239.387 383.448 235.829 383.448 231.439C383.448 227.05 379.889 223.491 375.5 223.491C371.11 223.491 367.552 227.05 367.552 231.439C367.552 235.829 371.11 239.387 375.5 239.387Z" fill="#E6E6E6"/>
<path d="M434 216.387C438.389 216.387 441.948 212.829 441.948 208.439C441.948 204.05 438.389 200.491 434 200.491C429.61 200.491 426.052 204.05 426.052 208.439C426.052 212.829 429.61 216.387 434 216.387Z" fill="#3F3D56"/>
</g>
<defs>
<clipPath id="clip0_1403_1652">
<rect width="441.895" height="238.774" fill="white" transform="translate(0.0527344 0.613159)"/>
</clipPath>
</defs>
</svg>

                            <div class="col-span-12 mt-8 text-center">
                                <p-button label="Go to Dashboard" routerLink="/" severity="danger" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`
})
export class Error {}
