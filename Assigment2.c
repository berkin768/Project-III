//gcc Assigment2.c -o Assigment2.out
//./Assigment2.out

#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>

struct City {
  int plateNumber;
  char name[32];
  double area;
  int population;
  int populationDensity;
  int cityCenterPopulation;
  char region[32];
  double latitude;
  double longitude;
};

static struct City cities[82];
#define structSize sizeof(struct City)
#define cityNumber sizeof(cities) / sizeof(struct City)

void printCityData(); //cities is an array of struct City
void readFile();
void quickSort( int a[], int l, int r);
void quickSort( int a[], int l, int r);
void findMedians();
void menu();
void insertData();

void readFile(){
  int fd = open("data.dat", O_RDONLY);
  if(fd<0) {
    printf("Error while opening file!");
    return;
  }
  int index = 0;

  while(read(fd,&cities[index], structSize)>0) {
    index++;
  }
  close(fd);
}

void buildIndex(){
  //TODO
}

void printCityData() {
  int i;
  for (i = 0; i < cityNumber; i++) {
    printf("-----CITIES-----\n");
    printf("plateNumber: %d\n", cities[i].plateNumber);
    printf("name: %s\n", cities[i].name);
    printf("area: %lf\n", cities[i].area);
    printf("population: %d\n", cities[i].population);
    printf("populationDensity: %d\n", cities[i].populationDensity);
    printf("cityCenterPopulation: %d\n", cities[i].cityCenterPopulation);
    printf("region: %s\n", cities[i].region);
    printf("latitude: %lf\n", cities[i].latitude);
    printf("longitude: %lf\n", cities[i].longitude);
  }
}

void searchData(){
  //TODO
}

void insertData() {
  char name[32];
  double latitude;
  double longitude;
  cities[cityNumber-1].plateNumber = cityNumber;
  puts("City Name >");
  scanf("%32s", name);
  strcpy(cities[cityNumber-1].name,name);
  cities[cityNumber-1].area = 200;
  cities[cityNumber-1].population = 500000;
  cities[cityNumber-1].populationDensity = 120;
  cities[cityNumber-1].cityCenterPopulation = 250000;
  strcpy(cities[cityNumber-1].region,"NEW");
  puts("City Latitude > ");
  scanf("%lf", &latitude);
  cities[cityNumber-1].latitude = latitude;
  puts("City Longitude > ");
  scanf("%lf", &longitude);
  cities[cityNumber-1].longitude = longitude;
}

void findMedians(){
  int i;
  int latitudes[cityNumber];
  int longitudes[cityNumber];

  for (i = 0; i < cityNumber; i++) {
    latitudes[i] = cities[i].latitude;
    longitudes[i] = cities[i].longitude;
  }

  quickSort(latitudes,0,cityNumber-1);
  quickSort(longitudes,0,cityNumber-1);

  double medianLatitude = (double)latitudes[cityNumber/2];
  double medianLongitude = (double)longitudes[cityNumber/2];

  printf("Latitude Median > %lf\n", medianLatitude);
  printf("Longitude Median > %lf\n", medianLongitude);
}

void quickSort( int a[], int l, int r){
  int j;
  if( l < r ){
    j = partition( a, l, r);
    quickSort( a, l, j-1);
    quickSort( a, j+1, r);
  }
}

int partition( int a[], int l, int r) {
  int pivot, i, j, t;
  pivot = a[l];
  i = l; j = r+1;
  while( 1){
    do ++i; while( a[i] <= pivot && i <= r );
    do --j; while( a[j] > pivot );
    if( i >= j ) break;
    t = a[i]; a[i] = a[j]; a[j] = t;
  }
  t = a[l]; a[l] = a[j]; a[j] = t;
  return j;
}

void menu(){
  puts("1 - Read From Data");
  puts("2 - List Cities");
  puts("3 - Medians (TEST)");
  puts("4 - Insert New Data");
  puts("5 - Search Data");
  puts("6 - Exit");

  int command;
  scanf("%d", &command);
  switch (command) {
    case 1:
    readFile();
    puts(".DAT LOADED");
    break;
    case 2:
    printCityData();
    break;
    case 3:
    findMedians();
    break;
    case 4:
    insertData();
    break;
    case 5:
    searchData();
    break;
    case 6:
    puts("BYE");
    exit(0);
    break;
    default:
    puts("Wrong Input");
    exit(0);
    break;
  }
}

int main(int argc, char const *argv[]) {
  while(1){
    menu();
  }
  return 0;
}
