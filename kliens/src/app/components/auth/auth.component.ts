import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AuthService, RegisterForm } from "src/app/core/service/auth.service";
import { UtilService } from "src/app/core/service/util.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit, OnDestroy {

  registerForm = new FormGroup<RegisterForm>({
    username: new FormControl<string>('', [Validators.required]),
    password: new FormControl<string>('', [Validators.required]),
    firstName: new FormControl<string>('', [Validators.required]),
    lastName: new FormControl<string>('', [Validators.required])
  })

  loginUserNameFormControl = new FormControl(null, Validators.required);
  loginPasswordFormControl = new FormControl(null, Validators.required);
  loginFormGroup = new FormGroup({
    loginUserNameFormControl: this.loginUserNameFormControl,
    loginPasswordFormControl: this.loginPasswordFormControl
  });

  subscriptionContainer = new Subscription();

  constructor(private authService: AuthService,
    private utilService: UtilService
  ) {

  }
  ngOnDestroy(): void {
    this.subscriptionContainer.unsubscribe();
  }

  ngOnInit(): void { }

  signIn() {
    if (!this.loginFormGroup.valid) {
      this.utilService.showSnackBar('error', 'Missing login credentials!');
      return;
    }
    this.subscriptionContainer.add(this.authService.signIn(this.loginFormGroup.controls.loginUserNameFormControl.value, this.loginFormGroup.controls.loginPasswordFormControl.value));
  }

  signUp() {
    this.subscriptionContainer.add(this.authService.signUp(this.registerForm));
  }

}
