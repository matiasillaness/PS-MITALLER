import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-page',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './page.component.html',
  styleUrl: './page.component.css'
})
export class PageComponent {



  title = 'mitallerf';
  loadScript(url: string) {
    const script = document.createElement('script');
    script.src = url;
    document.body.appendChild(script);
  }

  ngOnInit() {
    this.loadScript('assets/js/main.js');
  }


}

