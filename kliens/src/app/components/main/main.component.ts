import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatIconRegistry } from "@angular/material/icon";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Subscription, tap } from "rxjs";
import { AuthService } from "src/app/core/service/auth.service";
import { UserOperationDialog } from "src/app/shared/components/user-operation/user-operation.component";
import { UserData } from "src/app/shared/model/entities/user-data";


 @Component({
  selector: 'app-sidebar',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MainComponent implements OnInit, OnDestroy {

     imgUrl: string = "../assets/profile-placeholder.png";

     isOpened: boolean = true;

     userData$ = this.authService.userData$.pipe(
      tap(userData => {
        if (userData) {
          this.imgUrl = userData.image ? 'data:image/png;base64,' + userData.image : "../assets/profile-placeholder.png";
        }
      })
     );

     subscrtiptionContainer = new Subscription();

     /**
      *
      */
     constructor(
      private authService: AuthService,
      private dialog: MatDialog
    ) { }


   ngOnDestroy(): void {
    this.subscrtiptionContainer.unsubscribe();
   }

    ngOnInit(): void {
      this.subscrtiptionContainer.add(this.authService.checkSession(localStorage.getItem('sessionId')));
    }

    toggleSidebar() {
    this.isOpened = !this.isOpened
    }

    logout() {
      this.authService.logOut();
    }

    openUserOperationDialog(userData: UserData) {
      this.authService.openUserOperationDialog(userData);
    }
}
