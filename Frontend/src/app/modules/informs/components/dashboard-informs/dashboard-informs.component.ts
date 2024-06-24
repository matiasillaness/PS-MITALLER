import { Component, OnInit } from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexTitleSubtitle,
  ApexStroke,
  ApexGrid,
  ApexNonAxisChartSeries,
  ApexLegend,
  ApexPlotOptions,
  ApexResponsive
} from 'ng-apexcharts';
import { InformsService } from '../../services/informs.service';


export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  dataLabels: ApexDataLabels;
  grid: ApexGrid;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
};

export type ChartOptionsCircle = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  colors: string[];
  legend: ApexLegend;
  plotOptions: ApexPlotOptions;
  responsive: ApexResponsive | ApexResponsive[];
};

@Component({
  selector: 'app-dashboard-informs',
  templateUrl: './dashboard-informs.component.html',
  styleUrls: ['./dashboard-informs.component.css']
})
export class DashboardInformsComponent implements OnInit{
  public chartOptionsPlataFacturada: Partial<ChartOptions>;
  public chartOptionsPlataInvertida: Partial<ChartOptions>;
  public chartOptions: Partial<ChartOptionsCircle>;
  ganacias: number = 0;
  gastos: number = 0;
  total: number = 0;
  
  constructor(private _informsService: InformsService) {
    this.chartOptions = {
      series: [],
      chart: {
        height: 390,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          offsetY: 0,
          startAngle: 0,
          endAngle: 270,
          hollow: {
            margin: 5,
            size: "30%",
            background: "transparent",
            image: undefined
          },
          dataLabels: {
            name: {
              show: false
            },
            value: {
              show: false
            }
          }
        }
      },
      colors: ["#1ab7ea", "#0084ff", "#39539E", "#0077B5"],
      labels: [

      ],
      legend: {
        show: true,
        floating: true,
        fontSize: "16px",
        position: "left",
        offsetX: 50,
        offsetY: 10,
        labels: {
          useSeriesColors: true
        },
        formatter: function(seriesName, opts) {
          return seriesName + ":  " + opts.w.globals.series[opts.seriesIndex];
        },
        itemMargin: {
          horizontal: 3
        }
      },
    
    
    };


    this.chartOptionsPlataFacturada = {
      series: [
        {
          name: 'Plata Facturada',
          data: [null]
        }
      ],
      chart: {
        height: 350,
        type: 'line',
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      title: {
        text: 'Dinero Facturado por Mes en el Año Actual',
        align: 'left'
      },
      grid: {
        row: {
          colors: ['#f3f3f3', 'transparent'],
          opacity: 0.5
        }
      },
      xaxis: {
        categories: [
          'Enero', 'Febrero', 
          'Marzo', 'Abril', 'Mayo', 
          'Junio', 'Julio', 'Agosto', 
          'Septiembre', 'Octubre', 
          'Noviembre', 'Diciembre']
      }
    };

    this.chartOptionsPlataInvertida = {
      series: [
        {
          name: 'Plata Gastada',
          data: [null]
        }
      ],
      chart: {
        height: 350,
        type: 'line',
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      title: {
        text: 'Dinero Gastado por Mes en el Año Actual',
        align: 'left'
      },
      grid: {
        row: {
          colors: ['#f3f3f3', 'transparent'],
          opacity: 0.5
        }
      },
      xaxis: {
        categories: [
          'Enero', 'Febrero', 
          'Marzo', 'Abril', 'Mayo', 
          'Junio', 'Julio', 'Agosto', 
          'Septiembre', 'Octubre', 
          'Noviembre', 'Diciembre']
      }
    };
  }

  ngOnInit(): void {
    this._informsService.getPlataPorMes().subscribe((data) => {
      this.chartOptionsPlataFacturada.series = [
        {
          name: 'Plata Facturada',
          data: data.map((p) => p.total)
        }
       
      ];
    });


    this._informsService.getPlataQueMasSeGasta().subscribe((data) => {

      this.chartOptionsPlataInvertida.series = [
        {
          name: 'Plata Invertida',
          data: data.map((p) => p.total)
        }
      ];
    });

    this._informsService.getServiciosMasUtilizados().subscribe((data) => {
      console.log(data);
      this.chartOptions.series = data.map((s) => s.cantidad);
      this.chartOptions.labels = data.map((s) => s.nombre);
      setTimeout(()=> (window as any).dispatchEvent(new Event('resize')), 1)
    });

    this._informsService.getTotalGanado().subscribe((data) => {
      this.ganacias = data;
    });

    this._informsService.getTotalGastado().subscribe((data) => {
      this.gastos = data;
    });
  }
}
