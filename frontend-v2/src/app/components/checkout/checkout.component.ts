import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AddressService } from '../../domain/address/address.service';
import { ProvinceDto } from './../../domain/address/province-dto';
import { Component, OnInit } from '@angular/core';
import { DistrictDto } from '../../domain/address/district-dto';
import { WardDto } from '../../domain/address/ward-dto';

const ADDRESS_COMPARATOR = (a: { name: string }, b: { name: string }) =>
  a.name.localeCompare(b.name);

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss',
})
export class CheckoutComponent implements OnInit {
  addrFormGroup = new FormGroup({
    provinceId: new FormControl<number>(-1),
    districtId: new FormControl<number>(-1),
    ward: new FormControl<number>(-1),
  });
  provinces: ProvinceDto[] = [];
  districts: DistrictDto[] = [];
  wards: WardDto[] = [];

  constructor(private readonly addrService: AddressService) {}

  ngOnInit(): void {
    this.listProvinces();
  }

  listProvinces(): void {
    this.addrService.getProvinces().subscribe((provinceDtoPage) => {
      this.provinces = provinceDtoPage.items;
      this.provinces.sort(ADDRESS_COMPARATOR);
    });
  }

  handleProvinceSelected(event: Event): void {
    const selectedProvinceId = parseInt(
      (event.target as HTMLSelectElement).value
    );
    this.wards = [];
    this.addrFormGroup.patchValue({ districtId: -1, ward: -1 });
    if (selectedProvinceId > -1) {
      this.addrService
        .getDistricts(selectedProvinceId)
        .subscribe((districtDtoPage) => {
          this.districts = districtDtoPage.items;
          this.districts.sort(ADDRESS_COMPARATOR);
        });
      return;
    }
    this.districts = [];
  }

  handleDistrictSelected(event: Event): void {
    const selectedWardId = parseInt((event.target as HTMLSelectElement).value);
    if (selectedWardId > -1) {
      this.addrService.getWards(selectedWardId).subscribe((wardDtoPage) => {
        this.wards = wardDtoPage.items;
        this.wards.sort(ADDRESS_COMPARATOR);
      });
      return;
    }
  }
}
