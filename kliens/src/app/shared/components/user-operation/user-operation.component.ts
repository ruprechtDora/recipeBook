import { OnInit, OnDestroy, Component, ChangeDetectionStrategy, Inject } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { UserData } from "../../model/entities/user-data";
import { AuthService, UserDataForm } from "src/app/core/service/auth.service";
import { Subscription } from "rxjs";


@Component({
  selector: 'app-user-operation',
  templateUrl: 'user-operation.component.html',
  styleUrls: ['user-operation.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserOperationDialog implements OnInit, OnDestroy {
  firstNameControl = new FormControl<string>('', [Validators.required]);
  lastNameControl = new FormControl<string>('', [Validators.required]);
  newPasswordControl = new FormControl<string>('', [Validators.required]);
  oldPasswordControl = new FormControl<string>('', [Validators.required]);
  confirmPasswordControl = new FormControl<string>('', [Validators.required]);
  imageControl = new FormControl<string>('');


  imageUrl!:string;
  passwordHidden = true;

  userDataForm = new FormGroup<UserDataForm>({
    firstName: this.firstNameControl,
    lastName: this.lastNameControl,
    password: this.newPasswordControl,
    image: this.imageControl
  })

  subcriptionContainer = new Subscription();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: UserData,
    private authService: AuthService
  ) {

  }
  ngOnDestroy(): void {
    this.subcriptionContainer.unsubscribe();
  }
  ngOnInit(): void {
    this.loadUserDataValues();
  }


  updateProfile() {
    this.subcriptionContainer.add(this.authService.updateUserProfile(this.userDataForm, this.data.id));
  }


  togglePasswordHidden() {
    this.passwordHidden = !this.passwordHidden;
  }

  changeImage(event: any) {
    this.authService.uploadProfileImage(event.target.files[0], this.data.id);
  }

  loadUserDataValues() {
    this.firstNameControl.setValue(this.data.firstName);
    this.lastNameControl.setValue(this.data.lastName);
  }


}
