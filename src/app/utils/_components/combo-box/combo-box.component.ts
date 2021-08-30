import { Component, OnInit, Input, OnDestroy, Output, EventEmitter, forwardRef, AfterViewInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Option } from '../../_model/option';
import { BehaviorSubject, Subscription } from 'rxjs';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, } from '@angular/forms';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';

declare var $: any;

const sampleOptions: Option[] = [
  {
    id: '1', value: '1', text: "Level 1 #1",
    children: [
      { id: '2', value: '1.1', text: "Level 2 #1-1" },
      { id: '3', value: '1.2', text: "Level 2 #1-2" },
      { id: '4', value: '1.3', text: "Level 2 #1-3" },
      { id: '5', value: '1.4', text: "Level 2 #1-4" },
      { id: '6', value: '1.5', text: "Level 2 #1-5" },
    ]
  },
  {
    id: '7', value: '2', text: "Level 1 #2",
    children: [
      { id: '8', value: '2.1', text: "Level 2 #2-1" },
      { id: '9', value: '2.2', text: "Level 2 #2-2" },
      { id: '10', value: '2.3', text: "Level 2 #2-3" },
      { id: '11', value: '2.4', text: "Level 2 #2-4" },
      { id: '12', value: '2.5', text: "Level 2 #2-5" },
    ]
  },
  {
    id: '13', value: '13', text: "Level 1 #3",
    children: [
      { id: '14', value: '3.1', text: "Level 2 #2-1" },
      { id: '15', value: '3.2', text: "Level 2 #2-2" },
      { id: '16', value: '3.3', text: "Level 2 #2-3" },
      { id: '17', value: '3.4', text: "Level 2 #2-4" },
      { id: '18', value: '3.5', text: "Level 2 #2-5" },
    ]
  }
]
const empty = { id: '0', value: '', text: '' };

@Component({
  selector: 'pakar-combo-box-accordion',
  templateUrl: './combo-box.component.html',
  styleUrls: ['./combo-box.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ComboBoxComponent),
      multi: true
    },
  ]
})
export class ComboBoxComponent implements OnInit, OnDestroy, ControlValueAccessor, AfterViewInit {

  @Input() options: BehaviorSubject<Option[]>;
  @Input() placeholder: string;
  @Input() disabled: boolean = false;
  @Output() onChange = new EventEmitter<any>();

  @ViewChild('comboBoxDrop') comboBoxDrop: NgbDropdown;
  @ViewChild('comboBox') comboBox: ElementRef<HTMLDivElement>;

  selected: Option = { id: '0', value: '', text: '' };
  hasError: boolean = false;
  subscriptions: Subscription[] = [];
  randomId: number = Math.ceil(Math.random() * 1000000);
  tree_id = ".combo-box-tree";
  datasource: Option[];

  constructor() {
    this.tree_id = `#tree-cbx-${this.randomId}`;
  }

  toogleDropdown(e) {
    if (!this.disabled) {
      e.stopPropagation();
      this.comboBoxDrop.open();
    }
  }

  openChange(open: boolean) {
    const div = this.comboBox.nativeElement;
    if (div) {
      if (open) {
        div.classList.add('focus');
      } else {
        div.classList.remove('focus');
      }
    }
  }

  // onClick(item) {
  //   this.onSelect(item);
  // }

  onSelect(item: Option) {
    this.selected = item;
    this.onChange.emit(item);
    this.propogateChange(item.id);
    this.comboBoxDrop.close();
  }

  private findNode(id: number, datasource: Option[] = null): Option {
    if (datasource == null) {
      datasource = this.datasource;
    }
    let found: Option = datasource.find(d => parseInt(d.id) == id);
    if (!found) {
      datasource.forEach(d => {
        if (found) return;
        if (d.children && d.children.length) {
          found = this.findNode(id, d.children)
        }
      })
    }
    return found;
  }

  selectNode(id) {
    const found = this.findNode(parseInt(id));
    if (found.children && found.children.length) {
      return;
    }
    this.onSelect(found);
  }

  ngOnInit(): void {
    if (!this.placeholder) this.placeholder = 'Pilih lokasi yang tepat untuk artikel yang ingin ditambahkan';
  }

  ngAfterViewInit(): void {
    this.initJsTree();
    const subscribe = this.options.subscribe(resp => {
      this.datasource = resp;
      const $tree = $(this.tree_id).jstree(true);
      $tree.settings.core.data = this.datasource;
      $tree.refresh(true);
    });
    this.subscriptions.push(subscribe);
  }

  private initJsTree() {
    const $this = this;
    $(this.tree_id).jstree({
      "core": {
        "multiple": false,
        "check_callback": true,
        "themes": {
          "responsive": false,
          "variant": "small",
          icons: true,
        },
        "data": [],
      },
      "types": {
        "default": {
          "icon": "ki ki-arrow-next icon-xs pakar-color-dark"
        },
        'f-open': {
          'icon': 'ki ki-arrow-down icon-xs pakar-color-dark'
        },
        'f-closed': {
          'icon': 'ki ki-arrow-next icon-xs pakar-color-dark'
        }
      },
      "state": {
        "key": "demo1"
      },
      "plugins": ['themes', "types"]
    });
    $(this.tree_id).on('open_node.jstree', function (event, data) {
      data.instance.set_type(data.node, 'f-open');
    });
    $(this.tree_id).on('close_node.jstree', function (event, data) {
      data.instance.set_type(data.node, 'f-closed');
    });
    $(this.tree_id).on("changed.jstree", function (e, data) {
      if (data && data.action == "select_node") {
        data.instance.toggle_node(data.node);
      }
    });
    $(this.tree_id).on('dblclick', '.jstree-anchor', function (e) {
      console.log('dblclick', { e });
      let instance = $.jstree.reference(this),
        node = instance.get_node(e.target);
      if (node) {
        $this.selectNode(parseInt(node.id));
      }
      e.stopPropagation();
      e.preventDefault();
      return false;
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  // ControlValueAccessor interface
  propogateChange: (_) => {}
  writeValue(value: any): void {
    if (value) {
      this.selected = value;
    } else {
      this.selected = { id: '0', value: '', text: '' };
    }
    // this.cdr.detectChanges();
  }
  registerOnChange(fn: any): void {
    this.propogateChange = fn;
  }
  registerOnTouched(fn: any): void { }
  setDisabledState?(isDisabled: boolean): void {
    throw new Error("Method not implemented.");
  }

}
