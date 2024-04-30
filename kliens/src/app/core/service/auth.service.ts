import { Injectable } from "@angular/core";
import { ApiService } from "../http/api.service";
import { FormControl, FormGroup } from "@angular/forms";
import { RegisterRequest } from "src/app/shared/model/request/register-request";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { UtilService } from "./util.service";
import { LoginRequest } from "src/app/shared/model/request/login-request";
import { BehaviorSubject } from "rxjs";
import { UserData } from "src/app/shared/model/entities/user-data";
import { UpdateUserDataRequest } from "src/app/shared/model/request/update-user-data-request";
import { MatDialog } from "@angular/material/dialog";
import { UserOperationDialog } from "src/app/shared/components/user-operation/user-operation.component";

export interface RegisterForm {
  username: FormControl<string>,
  password: FormControl<string>,
  firstName: FormControl<string>,
  lastName: FormControl<string>
}

export interface UserDataForm {
  firstName: FormControl<string>,
  lastName: FormControl<string>,
  image: FormControl<string>,
  password: FormControl<string>
}


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  userDataSubject = new BehaviorSubject<UserData>(null);
  userData$ = this.userDataSubject.asObservable();


  constructor(
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private router: Router,
    private utilService: UtilService,
    private dialog: MatDialog
  ) {
  }

  signUp(registerForm: FormGroup<RegisterForm>) {
    console.log(registerForm);
    const request = this.createRegisterRequest(registerForm);
    return this.apiService.signUp(request).subscribe({
      next: response => this.utilService.showSnackBar('success', 'Your registration has been successfull!')
    });
  }

  signIn(username: string, password: string) {
    const loginRequest = {
      username,
      password
    } as LoginRequest

    return this.apiService.signIn(loginRequest).subscribe({
      next: response => {
        this.handleSessionId(response.sessionId);
        this.router.navigate(['/recipes']);
      },
      error: (err) => this.utilService.showSnackBar('error','Incorrect credentials!')
    });
  }

  checkSession(sessionId: string) {
    return this.apiService.checkSession(sessionId).subscribe({
      next: (response) =>  {
        if (!response.isValid) {
          this.logOut();
        } else {
          this.loadUserData(sessionId);
        }
      }
    })
  }

  loadUserData(sessionId: string) {
    this.apiService.loadUserData(sessionId).subscribe({
      next: response => {
        this.nextUserDataSubjectValue(response);
      }
    });
  }

  logOut() {
    this.router.navigate(['/auth']);
  }

  createRegisterRequest(registerForm: FormGroup<RegisterForm>) {
    return {
      username: registerForm.controls.username.value,
      password: registerForm.controls.password.value,
      firstName: registerForm.controls.firstName.value,
      lastName: registerForm.controls.lastName.value
    } as RegisterRequest
  }

  handleSessionId(sessionId: string) {
    localStorage.setItem('sessionId', sessionId);
  }

  nextUserDataSubjectValue(userData: UserData) {
    this.userDataSubject.next(userData);
  }

  updateUserProfile(userDataForm: FormGroup<UserDataForm>, userId: number) {
    const request = this.createUpdateUserDataRequest(userDataForm, userId);
    return this.apiService.updateUserData(request).subscribe({
      next: response => {
        this.utilService.showSnackBar('success', 'Your profile has been updated successfully!');
        this.loadUserData(localStorage.getItem('sessionId'));
        this.dialog.closeAll();
      },
      error: err => this.utilService.showSnackBar('error', 'Error during updating user profile!')
    });
  }

  createUpdateUserDataRequest(userDataForm: FormGroup<UserDataForm>, userId: number) {
    return {
      id: userId,
      firstName: userDataForm.controls.firstName.value,
      lastName: userDataForm.controls.lastName.value,
      password: userDataForm.controls.password.value
    } as UpdateUserDataRequest
  }

  uploadProfileImage(file: File, userId: number) {
    return this.apiService.uploadProfilImage(file, userId).subscribe();
  }

  openUserOperationDialog(userData: UserData) {
    const dialogRef = this.dialog.open(UserOperationDialog, {data: userData});
  }

}
