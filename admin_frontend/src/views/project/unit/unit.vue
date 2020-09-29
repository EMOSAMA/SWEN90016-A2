<template>
  <div class="unit">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="课程名称" slot="search">
        <el-input v-model="searchForm.unitName" placeholder="课程名称"></el-input>
      </el-form-item>
      <el-form-item label="课程类型" slot="search">
        <el-select v-model="searchForm.unitType" clearable placeholder="请选择类型">
          <el-option v-for="item in unitTypes"
                     :label="item.type"
                     :key="item.type"
                     :value="item.type"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <div slot="table">
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column label="序号" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
          <el-table-column label="课程名称" prop="unitName" align="center"></el-table-column>
          <el-table-column label="课程描述" prop="unitDescription" align="center"></el-table-column>
          <el-table-column label="课程类型" prop="unitType" align="center"></el-table-column>
          <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </assets-page>
    <div class="dialog-wrapper">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="showDialog"
        :close-on-click-modal=false
        width="600px"
        @closed="dialogClosed">
        <el-form :model="formData" label-width="100px">
          <el-form-item label="课程名称">
            <el-input placeholder="请输入课程名称" v-model="formData.unitName"></el-input>
          </el-form-item>
          <el-form-item label="课程描述">
            <el-input placeholder="请输入课程描述" v-model="formData.unitDescription"></el-input>
          </el-form-item>

          <el-form-item label="课程类型">
            <el-select v-model="formData.unitType" clearable placeholder="请选择类型">
              <el-option :label="item.type" :value="item.type" v-for="(item, index) in unitTypes"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="save">保存</el-button>
            <el-button @click="toggleDialog">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/views/project/task/components/assets-page'
  import { queryUnit, addUnit, updateUnit, deleteUnit } from 'api/unitAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import Pagination from '@c/pagination/pagination'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          unitName: '',
          unitType: '',
          pageIndex: 1
        },
        formData: {
          unitName: '',
          unitDescription: '',
          unitType: '',
          id: ''
        },
        unitTypes:[
          { type: "专业课程"},
          { type: "公共课程"},
          { type: "线上课程"}
        ]
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      showAdd () {
        this.dialogTitle = '课程新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '课程编辑'
        this.setFormData(data)
        this.toggleDialog()
      },
      async save () {
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        if (this.formData.id) {
          res = await updateUnit(this.formData)
          message = '修改成功'
        } else {
          res = await addUnit(this.formData)
          message = '新增成功'
        }
        if (res.success) {
          this.$message.success(message)
          this.getPageData()
          this.toggleDialog()
        } else {
          this.save()
        }
      },
      async getPageData () {
        const res = await queryUnit(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteUnit(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.unitName) {
          this.$message.error('请填写课程名称')
          return false
        }
        if (!this.formData.unitType) {
          this.$message.error('请选择课程类型')
          return false
        }
        return true
      }
    },
    components: {
      AssetsPage,
      Pagination
    },
    computed: {
      routes () {
        return routes.student
      }
    }
  }
</script>

<style lang="scss" scoped>
  .unit {
    padding: 20px;

    .device-icon {
      font-size: 28px;
    }
  }
</style>
