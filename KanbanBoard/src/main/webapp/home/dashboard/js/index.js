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
function editWorkspace(name,id) {
    document.getElementById("wspName_edit").value = name;
    document.getElementById("id_thisWorkspace").value = id;
}
function deleteWorkspace(id) {
    document.getElementById("delete_wspID").value = id;
}
function shareWorkspaceToUser(id) {
    document.getElementById("id_workspace_to_share").value = id;
}
    let x = document.getElementById("alert");
    setTimeout(function(){ x.classList.add("displayNone")}, 6000);

