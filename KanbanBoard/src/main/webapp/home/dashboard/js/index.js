function addWorklist() {
    document.getElementById("btn_add_new_worklist").innerHTML = `
    <form class="add-list-btn" method="post" action="/workList?action=addWorkList">
    <div class="row" >
        <div class="col" >
            <label>
                <input type="text" name="newWorkList" placeholder="New work list name" id="ip_newWorkList">
            </label>
        </div>
        <div class="col" >
            <button type="submit" class="btn" >
                Add
            </button>
        </div>
    </div>
</form>
    `
}