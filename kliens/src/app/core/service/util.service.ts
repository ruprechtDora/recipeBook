import { Injectable } from "@angular/core";
import { MatSnackBar, MatSnackBarRef, TextOnlySnackBar } from "@angular/material/snack-bar";
import { ToastrService } from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(
    private toastr: ToastrService
  ) {

  }


  showSnackBar(type: string, message?: string): MatSnackBarRef<TextOnlySnackBar>  {
    if (type === 'success') {
      this.toastr.success(message);
    }
    if (type === 'error') {
      this.toastr.error(message);
    }
    return null;
  }

}
