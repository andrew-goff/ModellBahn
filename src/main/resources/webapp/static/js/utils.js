// module 'utils.js'
'use strict';

const Editable = {
  NEVER: 0,
  UPDATE: 1,
  ADD: 2
};

const EditMode = {
  VIEW: 0,
  UPDATE: 1,
  ADD: 2
};

const shouldDisable = (editable, editMode) => {
  if (editable === Editable.NEVER || editMode === EditMode.VIEW) {
    return true;
  }

  return (editable > editMode);
};

const apiRoot = () => {
  return location.protocol + '//' + location.host + '/ModellBahn/api/';
};

const siteRoot = () => {
  return location.protocol + '//' + location.host + '/ModellBahn/static/';
};

const fetchUrl = (dataType) => {
  let fetchUrl = apiRoot() + dataType;
  let searchParams = new URLSearchParams(location.search);

  if (searchParams.has('self')) {
    fetchUrl = searchParams.get('self');
  }

  return fetchUrl;
};

const removeChildren = (node) => {
  while (node.firstChild) {
    node.removeChild(node.firstChild);
  }
};

const reportError = (error) => {
  console.log(error);
  alert('error: ' + error.toString());
};

const addButton = (cell, lnk, action) => {
  removeChildren(cell);

  if (lnk) {
    cell.appendChild(getButton(lnk.href, lnk.rel, action));
  }
};

const getLink = (links, rel) => {
  if (!links) {
    return;
  }
  return links.find((lnk) => {
    return lnk.rel === rel;
  });
};

const getFieldId = (rowId, binding) => {
  return rowId + '_' + binding;
};

const getKeyId = (rowId) => {
  return getFieldId(rowId, 'key');
};

const getKeyValue = (rowId) => {
  let keyField = document.getElementById(getKeyId(rowId));
  return keyField.value;
};

const getRowId = (tableName, i) => {
  return tableName + '_' + i;
};

const getCellId = (rowId, column) => {
  if (column.binding) {
    return getFieldId(rowId, column.binding);
  } else {
    return getFieldId(rowId, 'buttons');
  }
};

const getCellRowId = (cell) => {
  return cell.id.substring(0, cell.id.lastIndexOf('_'));
};

const getImgSrc = (image) => {
  return 'img/' + image + '.png';
};

const getImg = (action) => {
  let img = document.createElement('img');

  img.alt = action;
  img.src = getImgSrc(action);

  return img;
};

const getButton = (value, alt, action) => {
  let btn = document.createElement('button');

  btn.setAttribute('value', value);
  btn.setAttribute('onclick', action);
  btn.className = 'nav-button';

  let img = getImg(alt);
  img.className = 'nav-button';

  btn.appendChild(img);

  return btn;
};

const addText = (cell, text) => {
  let txt = document.createTextNode(text);
  cell.appendChild(txt);
  return txt;
};

const getButtonLink = (href, alt, action) => {
  let a = document.createElement('a');

  a.setAttribute('href', href);
  a.className = 'nav-button';
  a.appendChild(getImg(action));

  return a;
};

const addOption = (select, value, text) => {
  let opt = document.createElement('option');
  opt.value = value;
  opt.text = text;
  select.add(opt);
};

const selectFile = (ctl, type, filter) => {
  //TODO: popup selector
};

class Column {
  constructor(heading, binding, getter, setter, editable, required, length) {
    this.heading = heading;
    this.binding = binding;
    this.getter = getter;
    this.setter = setter;
    this.editable = editable ? editable : Editable.NEVER;
    this.required = required ? required : false;
    this.length = Math.max(length ? length : heading.length,
        heading.length + 1);
    this.width = 0;
  }

  setTableName(tableName) {
    this.tableName = tableName;
  }

  getHeading() {
    let td = document.createElement('div');
    td.className = 'table-heading';
    addText(td, this.heading);
    return td;
  }

  entityValue(entity) {
    return this.getter(entity);
  }

  getLength() {
    return this.length;
  }

  getHeaderLength() {
    return this.heading.length;
  }

  getWidth() {
    return this.width;
  }

  setWidth(width) {
    this.width = width;
  }

  createControl() {
    return document.createElement('input');
  }

  getControl(cell, entity, editMode) {
    let ctl = this.createControl();

    let value;

    if (entity) {
      value = this.entityValue(entity);
    }

    if (value) {
      this.setValue(ctl, value);
    }

    if (value || entity) {
      ctl.disabled = shouldDisable(this.editable, editMode);
    } else {
      ctl.disabled = !(editMode === EditMode.ADD && this.editable
          !== Editable.NEVER);
    }

    ctl.readOnly = ctl.disabled;
    ctl.required = this.required;

    return ctl;
  }

  getValue(cell) {
    let ctl = cell.firstChild;
    if (ctl) {
      return this.getControlValue(ctl);
    }
  }

  getControlValue(ctl) {
    return ctl.value;
  }

  setValue(ctl, value) {
    ctl.value = value;
  }

  isButtons() {
    return false;
  }
}

class BoolColumn extends Column {
  constructor(heading, binding, getter, setter, editable, required) {
    super(heading, binding, getter, setter, editable, required, heading.length);
  }

  createControl() {
    let ctl = super.createControl();
    ctl.type = 'checkbox';
    return ctl;
  }

  getControlValue(ctl) {
    return ctl.checked;
  }

  setValue(ctl, value) {
    ctl.checked = value;
  }
}

class NumberColumn extends Column {
  constructor(heading, binding, getter, setter, editable, required, max, min,
      places) {
    max = max ? max : 255;
    super(heading, binding, getter, setter, editable, required,
        Math.max(max.toString().length, heading.length));
    this.max = max;
    this.min = min ? min : 0;
    this.places = places ? places : 0;
  }

  createControl() {
    let ctl = super.createControl();
    ctl.type = 'number';
    ctl.min = this.min;
    ctl.max = this.max;
    ctl.step = this.places > 0 ? 1 / this.places : 1;
    return ctl;
  }

  setValue(ctl, value) {
    ctl.value = parseFloat(value).toFixed(this.places);
  }
}

class PhoneColumn extends Column {
  constructor(heading, binding, getter, setter, editable, required) {
    super(heading, binding, getter, setter, editable, required);
  }

  createControl() {
    let ctl = super.createControl();
    ctl.type = 'tel';
    return ctl;
  }
}

class TextColumn extends Column {
  constructor(heading, binding, getter, setter, editable, required, length,
      pattern) {
    super(heading, binding, getter, setter, editable, required, length);
    this.pattern = pattern;
  }

  createControl() {
    let ctl = super.createControl();
    ctl.type = 'text';
    ctl.maxLength = this.length;
    ctl.pattern = this.pattern;
    return ctl;
  }
}

class URLColumn extends Column {
  constructor(heading, binding, getter, setter, editable, required) {
    super(heading, binding, getter, setter, editable, required);
  }

  createControl() {
    let ctl = super.createControl();
    ctl.type = 'url';
    ctl.pattern = '^(?:(http[s]?):\\/\\/)?(\\w+(?:\\.\\w+)*)(?::(\\d+))?(?:\\/(\\w+(?:\\/|\\.\\w+)?))?$';
    return ctl;
  }
}

class DateColumn extends TextColumn {
  constructor(heading, binding, getter, setter, editable, required) {
    super(heading, binding, getter, setter, editable, required, 12,
        '^(18[2-9][0-9]|19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[0-1])$');
  }

  createControl() {
    let ctl = super.createControl();
    ctl.addEventListener('click', (e) => {
    });
    return ctl;
  }

}

class IMGColumn extends Column {
  constructor(heading, binding, getter, setter, onChange, editable, required) {
    super(heading, binding, getter, setter, editable, required);
    this.onChange = onChange;
  }

  createControl() {
    let div = document.createElement('div');

    let ctl = getImg('none');
    ctl.className = 'img-';
    ctl.addEventListener('click', (e) => {
    });
    if (this.onChange) {
      ctl.change = this.onChange;
    }
    div.appendChild(ctl);

    let btn = document.createElement('button');
    btn.addEventListener('click', selectFile(ctl, 'image/*', false));
    btn.setAttribute('value', '+');
    btn.className = 'selection';
    div.appendChild(btn);

    return div;
  }

  getControlValue(ctl) {
    return ctl.src;
  }

  setValue(ctl, value) {
    ctl.src = value;
  }
}

class PDFColumn extends Column {
  constructor(heading, binding, getter, setter, onChange, editable, required) {
    super(heading, binding, getter, setter, editable, required);
    this.onChange = onChange;
  }

  getControl(cell, entity, editMode) {
    let ctl = this.createControl();

    let value;

    if (entity) {
      value = this.entityValue(entity);
    }

    if (value) {
      this.setValue(ctl, value);
    }

    if (value || entity) {
      ctl.disabled = shouldDisable(this.editable, editMode);
    } else {
      ctl.disabled = !(editMode === EditMode.ADD && this.editable
          !== Editable.NEVER);
    }

    ctl.required = this.required;

    return ctl;
  }

  createControl() {
    let ctl = document.createElement('a');
    ctl.addEventListener('click', (e) => {
    });
    if (this.onChange) {
      ctl.change = this.onChange;
    }

    let img = getImg('none');
    ctl.appendChild(img);

    let btn = document.createElement('button');
    btn.cick = selectFile(ctl, 'application/pdf', false);
    btn.setAttribute('value', '+');
    btn.className = 'selection';

    ctl.appendChild(btn);

    return ctl;
  }

  getControlValue(ctl) {
    return ctl.href;
  }

  setValue(ctl, value) {
    ctl.href = value;
  }
}

const closeAutoLists = (elmnt) => {
  let autoComp = document.getElementsByClassName('autocomplete-list');
  for (let i = 0 ; i < autoComp.length; i++) {
    autoComp[i].parent.removeChild(autoComp[i]);
  }
};

class AutoCompleteColumn extends Column {
  constructor(heading, binding, getter, setter, dropDown, editable, required) {
    super(heading, binding, getter, setter, editable, required, dropDown.length);
    this.options = dropDown.options;

    document.addEventListener('click', this.closeAllLists);
  }

  getControl(cell, entity, editMode) {
    let ctl = super.createControl();
    ctl.type = 'text';
    ctl.maxLength = this.length;
    ctl.addEventListener('input', (e) => { this.input(e); });
    ctl.addEventListener('keydown', (e) => { this.keydown(e); }); 
    return ctl;
  }

  getControlValue(ctl) {
    return ctl.code;
  }

  setValue(ctl, value) {
    ctl.code = value;    
    ctl.options.forEach((o) => {
      if (o.value === value) {
        ctl.value = o.display;
      }
    });
  }

  input(e) {
	let ctl = this;
    let inp = e.target;

    closeAutoLists(inp);

    if (!inp.value) {
      return false;
    }

    let autoComp = document.createElement('div');
    autoComp.className = 'autocomplete-list';

    inp.appendChild(autoComp);

    ctl.options.forEach((o) => {
      if (o.display.toLowerCase().includes(inp.value.toLowerCase())) {
   	    let autoItem = document.createElement('div');
        autoItem.code = o.value;
   	    autoItem.className = 'autocomplete-items';
        let txt = addText(autoItem, o.display.replace('/inp.value/i', '<strong>' + inp.value + '</strong>'));
        autoItem.addEventListener('click', this.click);
        autoComp.appendChild(autoItem);
      }
    });
  }

  click(e) {
	let o = e.target;
    let inp = o.parent;

    inp.value = e.target.value;
    inp.code = e.target.code;

    closeAutoLists(inp);
  };
  
  /*
  keydown = (e) => {
    ctl = this;
    inp = e.target;

    if (inp) x = x.getElementsByTagName('div');

    if (e.keyCode == 40) {
      currentFocus++;
      ctl.addActive(inp);
     } else if (e.keyCode == 38) {
       currentFocus--;
       ctl.addActive(inp);
     } else if (e.keyCode == 13) {
       e.preventDefault();
       if (currentFocus > -1) {
         if (inp) inp[currentFocus].click();
       }
     }
  };

  addActive = (inp) => {
    if (!inp) return false;

    removeActive(inp);
      
    if (currentFocus >= inp.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = (inp.length - 1);

    inp[currentFocus].classList.add('autocomplete-active');
  }
  
  removeActive = (inp) => {
    for (var i = 0; i < inp.length; i++) {
      inp[i].classList.remove('autocomplete-active');
    }
  }
  */
}

class SelectColumn extends Column {
  constructor(heading, binding, getter, setter, dropDown, editable, required) {
    super(heading, binding, getter, setter, editable, required,
        dropDown.length + 3.5);
    this.dropDown = dropDown;
    this.dropSize = 1;
  }

  getControl(cell, entity, editMode) {
    let ctl = this.createControl();

    let value;

    if (entity) {
      value = this.entityValue(entity);
    }

    if (value) {
      this.setValue(ctl, value);
    }

    if (value || entity) {
      ctl.disabled = shouldDisable(this.editable, editMode);
    } else {
      ctl.disabled = !(editMode === EditMode.ADD && this.editable
          !== Editable.NEVER);
    }

    ctl.required = this.required;

    return ctl;
  }

  addOptions(select, dropDown) {
    if (!this.required) {
      addOption(select, undefined, '(n/a)');
    }

    dropDown.options.forEach(option => {
      addOption(select, option.value, option.display);
    });
  }

  createControl() {
    let ctl = document.createElement('select');
    ctl.size = this.dropSize;
    this.addOptions(ctl, this.dropDown);
    return ctl;
  }

  getControlValue(select) {
    return select.options[select.selectedIndex].value;
  }

  getLength() {
    return Math.max(this.dropDown.length + 3.5, this.getHeaderLength());
  }

  setValue(ctl, value) {
    ctl.value = value;
    for (let i = 0; i < ctl.options.length; i++) {
      if (ctl.options[i].value === value) {
        ctl.selectedIndex = i;
        return;
      }
    }
  }
}

class ButtonColumn {
  constructor(headLinkage, btnLinkage) {
    this.headLinkage = headLinkage;
    this.btnLinkage = btnLinkage;
    this.length = Math.max(headLinkage.length, btnLinkage.length) * 8;
    this.width = 0;
  }

  setTableName(tableName) {
    this.tableName = tableName;
  }

  getHeading() {
    let td = document.createElement('div');
    td.className = 'table-heading-btn';

    let tableName = this.tableName;

    if (this.headLinkage) {
      this.headLinkage.forEach(linkage => {
        let btn = linkage(tableName);
        td.appendChild(btn);
      });
    } else {
      addText(td, ' ');
    }

    return td;
  }

  getLength() {
    return this.length;
  }

  getHeaderLength() {
    return this.length;
  }

  getWidth() {
    return this.width;
  }

  setWidth(width) {
    this.width = width;
  }

  getControl(cell, entity, editMode) {
    cell.className = 'table-btn';

    let rowId = getCellRowId(cell);
    let tableName = this.tableName;

    let ctl = document.createElement('div');

    if (editMode && this.btnLinkage) {
      this.btnLinkage.forEach(linkage => {
        if (entity) {
          let btn = linkage(tableName, rowId);
          ctl.appendChild(btn);
        }
      });
    } else {
      addText(ctl, ' ');
    }

    return ctl;
  }

  isButtons() {
    return true;
  }
}

async function checkResponse(response) {
  if (200 <= response.status && response.status <= 202) {
    return response.json();
  } else if (response.status == 204) {
    return {entities: [], links: []};
  }

  console.log(response);

  throw new Error(response.statusText);
}

async function modal(elementName, title, contentUrl) {
  let anchor = document.getElementById(elementName);
  let modal = document.getElementById('modal');

  if (anchor && !modal) {
    modal = document.createElement('div');
    modal.id = 'modal';
    modal.className = 'modal';

    let content = document.createElement('div');
    content.className = 'modal-content';

    let head = document.createElement('div');
    head.className = 'modal-header';

    let heading = document.createElement('h2');
    addText(heading, title);
    head.appendChild(heading);
    content.appendChild(head);

    let body = document.createElement('div');
    body.className = 'modal-body';

    let text = await fetch(contentUrl)
    .then(response => response.text())
    .catch(error => reportError(error));

    let area = document.createElement('textarea');
    area.value = text;
    area.color = 'black';
    area.height = '100%';
    area.width = '100%';
    area.readOnly = true;
    area.disabled = true;

    body.appendChild(area);
    content.appendChild(body);

    let foot = document.createElement('div');
    foot.className = 'modal-footer';

    content.appendChild(foot);
    modal.appendChild(content);

    anchor.appendChild(modal);
    anchor.href = '#';
    anchor.addEventListener('click', (e) => {
      modal.style.display = 'block';
    });

    window.addEventListener('click', (e) => {
      if (event.target === modal) {
        modal.style.display = 'none';
      }
    });
  }
}

const about = () => {
  modal('license', 'About ModellBahn', siteRoot() + 'LICENSE');
};

const setActiveTab = (event, tabName) => {
  let tabContents = document.getElementsByClassName('tabContent');
  let tabLinks = document.getElementsByClassName('tabLinks');

  for (let i = 0; i < tabContents.length; i++) {
    tabContents[i].style.display = (tabContents[i].id === tabName) ? 'block'
        : 'none';
  }

  let linkName = tabName.replace('Tab', 'Link');
  for (let i = 0; i < tabLinks.length; i++) {
    tabLinks[i].className = (tabLinks[i].id === linkName) ? 'tabLinks active' : 'tabLinks';
  }
};

const addRow = (tableName) => {
  return getButton(undefined, 'add', tableName + '.addRow()');
};

const deleteRow = (tableName, row) => {
  return getButton(row, 'delete', tableName + '.deleteRow(' + row + '.id)');
};

const editRow = (tableName, row) => {
  return getButton(row, 'update', tableName + '.editRow(' + row + '.id)');
};

const newRow = (tableName) => {
  return getButton(undefined, 'add', tableName + '.newRow()');
};

const updateRow = (tableName, row) => {
  return getButton(row, 'save', tableName + '.updateRow(' + row + '.id)');
};

const gridButtonColumn = (elementName) => {
  return new ButtonColumn([addRow],
      [updateRow, deleteRow]);
};

const addHeader = (tableName, table, columns, paged, rowCount) => {
  let isForm = paged === Paged.FORM;
  let header = document.createElement('div');
  header.id = tableName + '_thead';
  header.className = 'thead';
  table.append(header);

  let headRow = document.createElement('div');
  headRow.className = isForm ? 'form-head' : 'table-head';
  headRow.id = tableName + 'Head';
  header.append(headRow);

  columns.forEach(column => {
    if (isForm) {
      if (column.isButtons()) {
        let th = document.createElement('div');
        th.id = getCellId(getRowId(tableName, 0), column);
        th.className = 'table-btn';

        headRow.append(th);
      }
    } else {
      let th = column.getHeading();
      th.style.width = column.width;
      th.style.maxWidth = column.width;

      headRow.append(th);
    }
  });
};

const addBody = (tableName, table, pageSize, columns, paged, rowCount,
    maxLabel) => {
  let isForm = paged === Paged.FORM;
  let body = document.createElement('div');
  body.id = tableName + '_tbody';
  body.className = isForm ? 'flex-container' : 'tbody';
  table.append(body);

  let row;
  let maxRow = Math.max(rowCount, pageSize);
  for (row = 0; row < maxRow; row++) {
    let tr = document.createElement('div');
    let rowId = getRowId(tableName, row);
    tr.className = isForm ? 'flex-container' : 'table-row';
    tr.id = rowId;
    body.append(tr);

    let key = document.createElement('input');
    key.type = 'hidden';
    key.id = getKeyId(rowId);
    key.className = isForm ? 'flex-control' : 'table-cell';
    tr.append(key);

    columns.forEach(column => {
      let td = document.createElement('div');

      if (!isForm || !column.isButtons()) {
        if (isForm) {
          td.className = 'flex-item';

          let th = column.getHeading();
          th.className = 'flex-label';
          th.style.width = maxLabel + 'ch';
          th.style.maxWidth = maxLabel + 'ch';

          td.append(th);
        }

        let tc = document.createElement('div');
        tc.id = getCellId(rowId, column);
        tc.className = isForm ? 'flex-control' : 'table-cell';
        tc.style.width = isForm ? column.getLength() + 'ch' : column.getWidth();
        tc.style.maxWidth = tc.style.width;

        addText(tc, ' ');

        if (isForm) {
          td.append(tc);
          tr.append(td);
        } else {
          tr.append(tc);
        }
      }
    });
  }
};

const addFooter = (tableName, table, columns, paged, rowCount) => {
  let isForm = paged === Paged.FORM;
  let footer = document.createElement('div');
  footer.id = tableName + '_tfoot';
  footer.className = 'tfoot';
  table.append(footer);

  let navRow = document.createElement('div');
  navRow.className = isForm ? 'form-foot' : 'table-foot';
  navRow.id = tableName + 'Foot';
  footer.append(navRow);

  if (isForm) {
    let tf = document.createElement('div');
    tf.className = 'table-footer';
    tf.style.width = '100%';
    tf.style.maxWidth = '100%';

    addText(tf, ' ');

    navRow.append(tf);
  } else {
    for (let i = 0; i < columns.length; i++) {
      let tf = document.createElement('div');
      if (i === 0) {
        tf.className = 'table-prev';
        tf.id = tableName + 'Prev';
      } else if (i === (columns.length - 1)) {
        tf.className = 'table-next';
        tf.id = tableName + 'Next';
      } else {
        tf.className = 'table-footer';
      }

      tf.style.width = columns[i].getHeaderLength() + 'ch';
      tf.style.maxWidth = columns[i].getHeaderLength() + 'ch';

      addText(tf, ' ');

      navRow.append(tf);
    }
  }
};

async function uploadFile(url, inputCtl) {
  let formData = new FormData();

  let file = inputCtl.files[0];

  formData.append('FileName', file.name);
  formData.append('FileData', file);

  await fetch(url, {method: 'PUT', body: formData})
  .then(response => checkResponse(response))
  .catch(error => reportError(error));
}
