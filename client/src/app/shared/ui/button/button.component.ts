import {Component, input} from '@angular/core';

@Component({
  selector: 'btn',
  template: `
    <button class="btn">
      {{ text() }}
    </button>
  `,
  styles: `
    .btn {
      cursor: pointer;
      width: 250px;
      height: 75px;
      background: var(--white);
      border-color: var(--primary-light);
      border-width: 1px;
      border-style: solid;
      border-radius: 5px;

      text-align: center;
      color: var(--primary);
      font-family: var(--font-body);
      font-weight: var(--font-weight-body);
      line-height: var(--line-height-body);
      font-size: var(--text-medium);


    }

    .btn:hover {
      background: var(--primary);
      color: var(--white);
      box-shadow: 0 4px 4px 0 var(--primary);
    }

  `,
  standalone: true
})
export class ButtonComponent {
  text = input.required<string>();
  variant = input<'primary' | 'accent'>('primary');
}
