import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LandingPageComponent } from './modules/common/components/landing-page/landing-page.component';
import { ToolbarComponent } from './modules/common/components/toolbar/toolbar.component';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './modules/user/components/register/register.component';
import { LoginComponent } from './modules/user/components/login/login.component';
import { HeaderComponent } from './modules/common/components/header/header.component';
import { FooterComponent } from './modules/common/components/footer/footer.component';
import { FooterPartialComponent } from './modules/common/components/footer-partial/footer-partial.component';
import { UiUserComponent } from './modules/user/pages/ui-user/ui-user.component';
import { UiCarComponent } from './modules/car/page/ui-car/ui-car.component';
import { RegisterSupplierComponent } from './modules/supplier/components/register-supplier/register-supplier.component';
import { TableSupplierComponent } from './modules/supplier/components/table-supplier/table-supplier.component';
import { RegisterProductComponent } from './modules/inventory/components/register-product/register-product.component';
import { RegisterServiceComponent } from './modules/inventory/components/register-service/register-service.component';
import { TableProductComponent } from './modules/inventory/components/table-product/table-product.component';
import { TableServiceComponent } from './modules/inventory/components/table-service/table-service.component';
import { BrowserAnimationsModule, provideAnimations } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RegisterBrandComponent } from './modules/inventory/components/register-brand/register-brand.component';
import { TableBrandComponent } from './modules/inventory/components/table-brand/table-brand.component';
import { RegisterCarComponent } from './modules/car/components/register-car/register-car.component';
import { TableCarComponent } from './modules/car/components/table-car/table-car.component';
import { RegisterBrandCarComponent } from './modules/car/components/register-brand-car/register-brand-car.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { UiPanelAdminComponent } from './modules/common/page/ui-panel-admin/ui-panel-admin.component';
import { UiSupplierComponent } from './modules/supplier/page/ui-supplier/ui-supplier.component';
import { UiPaymentComponent } from './modules/payment/page/ui-payment/ui-payment.component';
import { UiOrderComponent } from './modules/orders/pages/ui-order/ui-order.component';
import { UiProductComponent } from './modules/inventory/page/ui-product/ui-product.component';
import { UiServiceComponent } from './modules/inventory/page/ui-service/ui-service.component';
import { UiBuyComponent } from './modules/buy/page/ui-buy/ui-buy.component'
import { MatTabsModule } from '@angular/material/tabs';
import { UpdateCarComponent } from './modules/car/components/update-car/update-car.component';
import { UpdateServeComponent } from './modules/inventory/components/update-serve/update-serve.component';
import { UpdateProductComponent } from './modules/inventory/components/update-product/update-product.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { HttpClientModule } from '@angular/common/http';
import { MatFormField, MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { LowerCaseFirstUpperPipe } from './core/pipes/lower-case-first-upper.pipe';
import { YesNoComponent } from './modules/common/components/yes-no/yes-no.component';
import { FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalMobileComponent } from './modules/common/components/modal-mobile/modal-mobile.component';
import { ModalUserClientComponent } from './modules/user/components/modal-user-client/modal-user-client.component';
import { ToastService, AngularToastifyModule } from 'angular-toastify'; 
import { ToastrModule, provideToastr } from 'ngx-toastr';
import { ModalBrandComponent } from './modules/car/components/modal-brand/modal-brand.component';
import { MatSelectModule } from '@angular/material/select';
import { DetailsCarComponent } from './modules/car/components/details-car/details-car.component';
import { PointsForObservationsPipe } from './core/pipes/points-for-observations.pipe';
import { TableUserComponent } from './modules/user/components/table-user/table-user.component';
import { RegisterUserAdminComponent } from './modules/user/components/register-user-admin/register-user-admin.component';
import { RegisterBuyComponent } from './modules/buy/components/register-buy/register-buy.component';
import { TableBuyComponent } from './modules/buy/components/table-buy/table-buy.component';
import { RegisterPaymentComponent } from './modules/payment/components/register-payment/register-payment.component';
import { TablePaymentComponent } from './modules/payment/components/table-payment/table-payment.component';
import { TermsComponent } from './modules/common/components/terms/terms.component';
import { OrderClientComponent } from './modules/orders/components/order-client/order-client.component';
import { OrderTableComponent } from './modules/orders/components/order-table/order-table.component';
import { ListOrderAvaiblesComponent } from './modules/orders/components/list-order-avaibles/list-order-avaibles.component';
import { RecoverAccountComponent } from './modules/user/components/recover-account/recover-account.component';
import { CreateOrdersComponent } from './modules/orders/components/create-orders/create-orders.component';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { DatePipe } from '@angular/common';
import { DetailsBuyComponent } from './modules/buy/components/details-buy/details-buy.component';
import { DetailsPaymentComponent } from './modules/payment/components/details-payment/details-payment.component';
import { DashboardInformsComponent } from './modules/informs/components/dashboard-informs/dashboard-informs.component';
import { NgApexchartsModule } from 'ng-apexcharts';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { roleAdminGuard } from './core/guards/role-admin.guard';
import { roleEmployerGuard } from './core/guards/role-employer.guard';
import { AccessDenieComponent } from './modules/common/components/access-denie/access-denie.component';
import { roleGuard } from './core/guards/role.guard';





const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'toolbar', component: ToolbarComponent }, //dsp borrar esto
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'terms', component: TermsComponent},
  { path: 'recover-account', component: RecoverAccountComponent},
  { path: 'order-client/:id', component: OrderClientComponent },
  { path: 'access-denied', component: AccessDenieComponent },
  { path: 'list-order-avaibles', component: ListOrderAvaiblesComponent},
  { //todo: RoleGuard es compartido el admin y empleado
    path: 'panel', component: UiPanelAdminComponent, canActivate: [roleGuard],children: [
      { path: 'user', component: UiUserComponent , children: [
        { path: 'register', component: RegisterUserAdminComponent, canActivate: [roleAdminGuard]},
        { path: 'table', component: TableUserComponent, canActivate: [roleAdminGuard] }

      ] },
      {
        path: 'informs', component: DashboardInformsComponent, children: [
          
        ]
      },
      {
        path: 'car', component: UiCarComponent, children: [
          { path: 'register', component: RegisterCarComponent },
          { path: 'table', component: TableCarComponent },
          { path: 'register-brand', component: RegisterBrandCarComponent },
          { path: 'update-car/:id', component: UpdateCarComponent },
          { path: 'update-car', component: UpdateCarComponent }
        ]
      },
      { path: 'supplier', component: UiSupplierComponent, children: [
        { path: 'register', component: RegisterSupplierComponent },
        { path: 'table', component: TableSupplierComponent }
      ] },
      { path: 'payment', component: UiPaymentComponent, children: [
        //todo: agregar componentes de payment
        { path: 'register', component: RegisterPaymentComponent },
        { path: 'table', component: TablePaymentComponent }
      ] },
      { path: 'order', component: UiOrderComponent , children: [
        {path: 'create-orders', component: CreateOrdersComponent},
        {path: 'table', component: OrderTableComponent}
      ] },
      { path: 'product', component: UiProductComponent, children: [
        { path: 'register', component: RegisterProductComponent },
        { path: 'table', component: TableProductComponent },
        { path: 'register-brand', component: RegisterBrandComponent },
        { path: 'update-product', component: UpdateProductComponent }
        
      ] },
      { path: 'service', component: UiServiceComponent , children: [
        { path: 'register', component: RegisterServiceComponent },
        { path: 'table', component: TableServiceComponent },
        { path: 'update-service', component: UpdateServeComponent }
      ] },
      { path: 'buy', component: UiBuyComponent, children: [
        { path: 'register', component: RegisterBuyComponent },
        { path: 'table', component: TableBuyComponent }
      ] },
    ]
  },
];



@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    ToolbarComponent,
    RegisterComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    FooterPartialComponent,
    UiUserComponent,
    UiCarComponent,
    RegisterSupplierComponent,
    TableSupplierComponent,
    RegisterProductComponent,
    RegisterServiceComponent,
    TableProductComponent,
    TableServiceComponent,
    RegisterBrandComponent,
    TableBrandComponent,
    RegisterCarComponent,
    TableCarComponent,
    RegisterBrandCarComponent,
    UiPanelAdminComponent,
    UiSupplierComponent,
    UiPaymentComponent,
    UiOrderComponent,
    UiProductComponent,
    UiServiceComponent,
    UiBuyComponent,
    UpdateCarComponent,
    UpdateServeComponent,
    UpdateProductComponent,
    LowerCaseFirstUpperPipe,
    YesNoComponent,
    ModalMobileComponent,
    ModalUserClientComponent,
    ModalBrandComponent,
    DetailsCarComponent,
    PointsForObservationsPipe,
    TableUserComponent,
    RegisterUserAdminComponent,
    RegisterBuyComponent,
    TableBuyComponent,
    RegisterPaymentComponent,
    TablePaymentComponent,
    TermsComponent,
    OrderClientComponent,
    OrderTableComponent,
    ListOrderAvaiblesComponent,
    RecoverAccountComponent,
    CreateOrdersComponent,
    DetailsBuyComponent,
    DetailsPaymentComponent,
    DashboardInformsComponent,
    AccessDenieComponent,
   


    

  ],
  imports: [
    MatProgressSpinnerModule,

    BrowserModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatSlideToggleModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    NgApexchartsModule,
    MatListModule,
    MatToolbarModule,
    MatTabsModule,
    MatCardModule,
    MatPaginatorModule,
    MatTableModule,
    
    MatSortModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    ToastrModule.forRoot(), // ToastrModule added

    
    
  
    

    
   
    //StoreModule.forRoot({}, {}), 
    //StoreDevtoolsModule.instrument({ name: 'TEST'}) 
  ],
  exports: [RouterModule],
  providers: [   provideAnimations(), // required animations providers
                provideToastr()],
                // Toastr providers],
  bootstrap: [AppComponent]
})
export class AppModule { }
